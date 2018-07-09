package com.beerboy.junit.core.rule;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.core.annotation.Messaging;
import com.beerboy.junit.core.annotation.Storage;
import com.beerboy.junit.core.api.Cleaner;
import com.beerboy.scanner.predicate.AnnotatedClassPredicate;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Set;

/**
 * @author manusant
 */
public class CleanerRule implements TestRule {

    private final Set<Cleaner> cleaners;

    public CleanerRule(Set<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                CleanUp annotation = description.getAnnotation(CleanUp.class);

                if (annotation != null && cleaners != null) {
                    if (annotation.storage().beforeClass() || annotation.storage().beforeTest()) {
                        cleaners.stream()
                                .filter(cleaner -> new AnnotatedClassPredicate(Storage.class).test(cleaner.getClass()))
                                .forEach(Cleaner::clean);
                    }
                    if (annotation.messages().beforeClass() || annotation.messages().beforeTest()) {
                        cleaners.stream()
                                .filter(cleaner -> new AnnotatedClassPredicate(Messaging.class).test(cleaner.getClass()))
                                .forEach(Cleaner::clean);
                    }
                }
                base.evaluate();
            }
        };
    }
}