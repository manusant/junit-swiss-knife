package com.beerboy.junit.guice;

import com.beerboy.junit.core.api.Loader;
import com.beerboy.junit.core.api.MessagingCleaner;
import com.beerboy.junit.core.api.Starter;
import com.beerboy.junit.core.api.StorageCleaner;
import com.beerboy.junit.core.rule.CleanerRule;
import com.beerboy.junit.core.rule.LoaderRule;
import com.beerboy.junit.core.rule.StarterRule;
import com.beerboy.junit.core.runner.SwissKnifeRunner;
import com.beerboy.junit.guice.annotation.MainModule;
import com.beerboy.junit.guice.di.MainModuleIT;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author manusant
 */
public class GuiceSwissKnifeRunner extends SwissKnifeRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceSwissKnifeRunner.class);

    private static Injector injector;

    public GuiceSwissKnifeRunner(Class<?> klass) throws InitializationError {
        super(klass);
        bootstrap();
    }

    private void bootstrap() {
        LOGGER.debug("Bootstraping Guice SwissKnifeRunner");
        if (injector == null) {
            MainModuleIT mainModule = resolveModule();
            injector = Guice.createInjector(mainModule);
            mainModule.onBind(injector);
            // Call starters here

            injector.findBindingsByType(TypeLiteral.get(Starter.class))
                    .stream()
                    .map(binding -> binding.getProvider().get())
                    .forEach(Starter::start);
        }
    }

    private MainModuleIT resolveModule() {
        TestClass testClass = getTestClass();
        MainModule mainModule = testClass.getAnnotation(MainModule.class);
        if (mainModule != null) {
            try {
                return mainModule.moduleClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Error while trying to instantiate main module class", e);
            }
        }
        throw new IllegalArgumentException("A main Guice Module is required when using Guice-Runner");
    }

    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        injector.injectMembers(obj);
        return obj;
    }

    @Override
    protected Optional<TestRule> starterRule() {
        Set<Starter> starters = injector.findBindingsByType(TypeLiteral.get(Starter.class))
                .stream()
                .map(binding -> binding.getProvider().get())
                .collect(Collectors.toSet());

        return starters.isEmpty() ? Optional.empty() : Optional.of(new StarterRule(starters));
    }

    @Override
    protected Optional<TestRule> loaderRule() {
        Set<Loader> loaders = injector.findBindingsByType(TypeLiteral.get(Loader.class))
                .stream()
                .map(binding -> binding.getProvider().get())
                .collect(Collectors.toSet());

        return loaders.isEmpty() ? Optional.empty() : Optional.of(new LoaderRule(loaders));
    }

    @Override
    protected Optional<TestRule> cleanerRule() {
        Set<MessagingCleaner> messagingCleaners = injector.findBindingsByType(TypeLiteral.get(MessagingCleaner.class))
                .stream()
                .map(binding -> binding.getProvider().get())
                .collect(Collectors.toSet());

        Set<StorageCleaner> storageCleaners = injector.findBindingsByType(TypeLiteral.get(StorageCleaner.class))
                .stream()
                .map(binding -> binding.getProvider().get())
                .collect(Collectors.toSet());

        return messagingCleaners.isEmpty() &&  storageCleaners.isEmpty()? Optional.empty() : Optional.of(new CleanerRule(messagingCleaners,storageCleaners));
    }
}
