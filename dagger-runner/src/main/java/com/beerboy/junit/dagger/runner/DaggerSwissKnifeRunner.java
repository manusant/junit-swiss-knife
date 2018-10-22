package com.beerboy.junit.dagger.runner;

import com.beerboy.junit.core.annotation.Messaging;
import com.beerboy.junit.core.annotation.Storage;
import com.beerboy.junit.core.api.*;
import com.beerboy.junit.core.rule.CleanerRule;
import com.beerboy.junit.core.rule.LoaderRule;
import com.beerboy.junit.core.rule.StarterRule;
import com.beerboy.junit.core.runner.SwissKnifeRunner;
import com.beerboy.junit.dagger.annotation.MainModule;
import com.beerboy.junit.dagger.di.DaggerSwissKnifeComponent;
import com.beerboy.junit.dagger.di.SwissKnifeComponent;
import com.beerboy.junit.dagger.di.SwissKnifeModule;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

public class DaggerSwissKnifeRunner extends SwissKnifeRunner {

    private SwissKnifeComponent swissKnifeComponent;

    private static final Logger LOGGER = LoggerFactory.getLogger(DaggerSwissKnifeRunner.class);

    public DaggerSwissKnifeRunner(Class<?> klass) throws InitializationError {
        super(klass);
        bootstrap();
    }

    private void bootstrap() {
        swissKnifeComponent = DaggerSwissKnifeComponent.builder()
                .swissKnifeModule(resolveModule())
                .build();
    }


    private SwissKnifeModule resolveModule() {
        TestClass testClass = getTestClass();
        MainModule mainModule = testClass.getAnnotation(MainModule.class);
        if (mainModule != null) {
            try {
                return mainModule.moduleClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Error while trying to instantiate main module class", e);
            }
        }
        throw new IllegalArgumentException("A main Dagger Module is required when using Dagger-Runner");
    }

    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        return obj;
    }

    @Override
    protected Optional<TestRule> starterRule() {
        Set<Starter> starters = swissKnifeComponent.getStarters();

        return starters.isEmpty() ? Optional.empty() : Optional.of(new StarterRule(starters));
    }

    @Override
    protected Optional<TestRule> loaderRule() {
        Set<Loader> loaders = swissKnifeComponent.getLoaders();

        return loaders.isEmpty() ? Optional.empty() : Optional.of(new LoaderRule(loaders));
    }

    @Override
    protected Optional<TestRule> cleanerRule() {
        Set<MessagingCleaner> messagingCleaners= swissKnifeComponent.getMessagingCleaners();
        Set<StorageCleaner> storageCleaners = swissKnifeComponent.getStorageCleaners();



        return messagingCleaners.isEmpty() &&  storageCleaners.isEmpty()? Optional.empty() : Optional.of(new CleanerRule(messagingCleaners,storageCleaners));
    }
}
