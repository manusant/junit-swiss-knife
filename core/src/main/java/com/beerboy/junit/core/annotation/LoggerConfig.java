package com.beerboy.junit.core.annotation;

import java.lang.annotation.*;

/**
 * @author manusant
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LoggerConfig {

    LogLevel level() default LogLevel.INFO;

    enum LogLevel {
        OFF,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE,
        ALL
    }
}