package com.beerboy.junit.core.runner;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.core.annotation.LoggerConfig;
import com.beerboy.junit.core.rule.CleanerRule;
import com.beerboy.junit.core.rule.LoggerConfigRule;
import com.beerboy.junit.core.rule.TestExecutionLogger;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author manusant
 */
public class SwissKnifeRunner extends BlockJUnit4ClassRunner {

    //private static Injector injector;
    private static CleanerRule cleanerRule = new CleanerRule();
    private static TestExecutionLogger testExecutionLogger = new TestExecutionLogger();
    private static final Logger LOGGER = LoggerFactory.getLogger(SwissKnifeRunner.class);

    public SwissKnifeRunner(Class<?> klass) throws InitializationError {
        super(klass);
        SwissKnifeRunner.bootstrap();
    }

    public static void bootstrap() {
        LOGGER.debug("Bootstraping SwissKnifeRunner");
       /* if (injector == null) {
            injector = Guice.createInjector(new ThorModuleIT());
            ThorModuleIT.setInjector(injector);
            ThorModuleIT.find(ThorServer.class).start();
            ThorModuleIT.find(SEDReactorClient.class).start();
            ThorModuleIT.find(TCCReactorClient.class).start();
        }*/
    }

    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        //injector.injectMembers(obj);
        return obj;
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

       /* if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(NetworkLoaderRule.class))) {
            rules.add(new NetworkLoaderRule());
        }

        if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(RestConfigRule.class))) {
            rules.add(new RestConfigRule());
        }*/

        CleanUp cleanUp = getTestClass().getAnnotation(CleanUp.class);
        if (cleanUp != null) {
            if (cleanUp.storage().beforeClass() && cleanUp.storage().beforeTest() || cleanUp.messages().beforeClass() && cleanUp.messages().beforeTest()) {
                throw new IllegalArgumentException("Only one CleanUp strategy is supported. Please configure 'beforeClass' or 'beforeTest' strategy");
            }
            if (!cleanUp.storage().beforeClass() && !cleanUp.storage().beforeTest()) {
                throw new IllegalArgumentException("At least one Storage CleanUp strategy is required. Please configure 'beforeClass' or 'beforeTest' strategy");
            }
            if (cleanUp.storage().beforeClass() || cleanUp.messages().beforeClass()) {
                if (rules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(CleanerRule.class))) {
                    rules.add(cleanerRule);
                }
            }
        } else {
            throw new IllegalArgumentException("@CleanUp annotation is required at Class Level");
        }

        return rules;
    }

    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> testRules = super.getTestRules(target);

        if (testRules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(TestExecutionLogger.class))) {
            testRules.add(testExecutionLogger);
        }

        CleanUp cleanUp = getTestClass().getAnnotation(CleanUp.class);

        if (cleanUp != null && (cleanUp.storage().beforeTest() || cleanUp.messages().beforeTest())) {
            if (testRules.stream().noneMatch(testRule -> testRule.getClass().isAssignableFrom(CleanerRule.class))) {
                testRules.add(cleanerRule);
            }
        }
        return testRules;
    }
}