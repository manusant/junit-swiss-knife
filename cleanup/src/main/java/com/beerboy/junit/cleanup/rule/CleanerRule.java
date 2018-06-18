package com.beerboy.junit.cleanup.rule;

import com.beerboy.junit.cleanup.annotation.CleanUp;
import com.beerboy.junit.cleanup.annotation.Storage;
import com.beerboy.junit.cleanup.strategy.Cleaner;
import com.beerboy.scanner.ClassScanner;
import com.beerboy.scanner.ScanResult;
import com.beerboy.scanner.stream.ClassStream;
import com.coriant.sdn.thor.di.ThorModule;
import com.coriant.sdn.thor.e2e.core.annotation.CleanUp;
import com.coriant.sdn.thor.e2e.core.messaging.mailbox.LeftWingMailbox;
import com.coriant.sdn.thor.service.IStoreWriter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author manusant
 */
public class CleanerRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                CleanUp annotation = description.getAnnotation(CleanUp.class);

                if (annotation != null) {
                    ScanResult scanResult = ClassScanner.scan(ClassLoader.getSystemClassLoader(), "com.beerboy.junit");

                    if (annotation.storage().beforeClass() || annotation.storage().beforeTest()) {

                        Set<Class> storageCleaners = scanResult
                                .stream()
                                .implementers(Cleaner.class)
                                .annotatedWith(Storage.class)
                                .collect(Collectors.toSet());

                        IStoreWriter storeWriter = ThorModule.find(IStoreWriter.class);
                        storeWriter.clear();
                    }
                    if (annotation.messages().beforeClass() || annotation.messages().beforeTest()) {
                        LeftWingMailbox mailbox = ThorModule.find(LeftWingMailbox.class);
                        mailbox.clear();
                    }
                }

                base.evaluate();
            }
        };
    }
}