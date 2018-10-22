package com.beerboy.junit.core.rule;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.core.api.MessagingCleaner;
import com.beerboy.junit.core.api.StorageCleaner;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Set;

/**
 * @author manusant
 */
public class CleanerRule implements TestRule {

    private final Set<MessagingCleaner> messagingCleaners;
    private final Set<StorageCleaner> storageCleaners;

    public CleanerRule(Set<MessagingCleaner> messagingCleaners, Set<StorageCleaner> storageCleaners) {
        this.messagingCleaners = messagingCleaners;
        this.storageCleaners = storageCleaners;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                CleanUp annotation = description.getAnnotation(CleanUp.class);

                if (annotation != null && messagingCleaners != null) {
                    if (annotation.messages().beforeClass() || annotation.storage().beforeTest()) {
                        messagingCleaners.forEach(MessagingCleaner::clean);
                    }
                }

                if (annotation != null && storageCleaners != null) {
                    if (annotation.storage().beforeClass() || annotation.messages().beforeTest()) {
                        storageCleaners.forEach(StorageCleaner::clean);
                    }
                }

                base.evaluate();
            }
        };
    }
}