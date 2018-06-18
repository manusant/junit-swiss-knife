package com.beerboy.junit.core.rule;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.beerboy.junit.core.annotation.LoggerConfig;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.LoggerFactory;

/**
 * @author manusant
 */
public class LoggerConfigRule implements TestRule {

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                LoggerConfig config = description.getAnnotation(LoggerConfig.class);
                if (config != null) {
                    Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
                    logger.setLevel(convertLevel(config.level()));
                }
                base.evaluate();
            }
        };
    }

    private Level convertLevel(final LoggerConfig.LogLevel level) {
        switch (level) {
            case OFF:
                return Level.OFF;
            case ERROR:
                return Level.ERROR;
            case WARN:
                return Level.WARN;
            case INFO:
                return Level.INFO;
            case DEBUG:
                return Level.DEBUG;
            case TRACE:
                return Level.TRACE;
            case ALL:
                return Level.ALL;
        }
        return Level.INFO;
    }
}