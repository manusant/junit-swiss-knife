package com.beerboy.junit.cleanup.annotation;

import java.lang.annotation.*;

/**
 * @author mfilho
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CleanUpStrategy {

    boolean beforeTest() default false;

    boolean beforeClass() default false;
}