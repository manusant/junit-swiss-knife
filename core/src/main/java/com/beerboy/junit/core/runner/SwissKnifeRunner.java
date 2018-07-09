package com.beerboy.junit.core.runner;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.core.annotation.LoggerConfig;
import com.beerboy.junit.core.rule.LoggerConfigRule;
import com.beerboy.junit.core.rule.TestExecutionLogger;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author manusant
 */
public class SwissKnifeRunner extends BlockJUnit4ClassRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwissKnifeRunner.class);

    private static final TestExecutionLogger testExecutionLogger = new TestExecutionLogger();

    public SwissKnifeRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<TestRule> classRules() {
        List<TestRule> rules = super.classRules();

        LoggerConfig loggerConfig = getTestClass().getAnnotation(LoggerConfig.class);
        if (loggerConfig != null) {
            if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(LoggerConfigRule.class))) {
                rules.add(new LoggerConfigRule());
            }
        }

        // bind starter rules
        starterRule().ifPresent(swissKnifeRule -> {
            if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(swissKnifeRule.getClass()))) {
                rules.add(swissKnifeRule);
            }
        });

        // bind loader rules
        loaderRule().ifPresent(swissKnifeRule -> {
            if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(swissKnifeRule.getClass()))) {
                rules.add(swissKnifeRule);
            }
        });

        CleanUp cleanUp = getTestClass().getAnnotation(CleanUp.class);
        if (cleanUp != null) {
            if (cleanUp.storage().beforeClass() && cleanUp.storage().beforeTest() || cleanUp.messages().beforeClass() && cleanUp.messages().beforeTest()) {
                throw new IllegalArgumentException("Only one CleanUp strategy is supported. Please configure 'beforeClass' or 'beforeTest' strategy");
            }
            if (!cleanUp.storage().beforeClass() && !cleanUp.storage().beforeTest()) {
                throw new IllegalArgumentException("At least one Storage CleanUp strategy is required. Please configure 'beforeClass' or 'beforeTest' strategy");
            }
            if (cleanUp.storage().beforeClass() || cleanUp.messages().beforeClass()) {
                cleanerRule().ifPresent(swissKnifeRule -> {
                    if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(swissKnifeRule.getClass()))) {
                        rules.add(swissKnifeRule);
                    }
                });
            }
        } else {
            throw new IllegalArgumentException("@CleanUp annotation is required at Class Level");
        }

        return rules;
    }

    protected Optional<TestRule> starterRule() {
        return Optional.empty();
    }

    protected Optional<TestRule> loaderRule() {
        return Optional.empty();
    }

    protected Optional<TestRule> cleanerRule() {

       /* ScanResult scanResult = ClassScanner.scan(ClassLoader.getSystemClassLoader(), "com.beerboy.junit");

        Set<Class> storageCleaners = scanResult
                .stream()
                .implementers(Cleaner.class)
                .annotatedWith(Storage.class)
                .collect(Collectors.toSet());*/

        return Optional.empty();
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> testRules = super.getTestRules(target);

        if (testRules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(TestExecutionLogger.class))) {
            testRules.add(testExecutionLogger);
        }

        CleanUp cleanUp = getTestClass().getAnnotation(CleanUp.class);

        if (cleanUp != null && (cleanUp.storage().beforeTest() || cleanUp.messages().beforeTest())) {
            cleanerRule().ifPresent(swissKnifeRule -> {
                if (testRules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(swissKnifeRule.getClass()))) {
                    testRules.add(swissKnifeRule);
                }
            });
        }
        return testRules;
    }
}