package com.beerboy.junit.core.rule;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author manusant
 */
public class TestExecutionLogger extends TestWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExecutionLogger.class);

    private String name;

    @Override
    protected void starting(Description d) {
        name = d.getMethodName();
        LOGGER.info("================================== START Test: " + name + " ======================================");
    }

    @Override
    protected void succeeded(Description description) {
        LOGGER.info("================================== END Test: " + name + "(SUCCESS) ======================================");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        LOGGER.info("================================== END Test: " + name + "(FAILED) ======================================");
    }
}