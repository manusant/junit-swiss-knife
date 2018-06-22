package com.beerboy.junit.core.rule;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.core.annotation.Storage;
import com.beerboy.junit.core.api.Cleaner;
import com.beerboy.scanner.ClassScanner;
import com.beerboy.scanner.ScanResult;

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

                       // Generic clean rule implementation goes here
                    }
                    if (annotation.messages().beforeClass() || annotation.messages().beforeTest()) {
                        // Generic clean rule implementation goes here
                    }
                }

                base.evaluate();
            }
        };
    }
}