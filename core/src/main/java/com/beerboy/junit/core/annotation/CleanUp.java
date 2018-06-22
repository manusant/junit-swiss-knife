package com.beerboy.junit.core.annotation;

import java.lang.annotation.*;

/**
 * @author manusant
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CleanUp {

    CleanUpStrategy storage() default @CleanUpStrategy(beforeClass = true);

    CleanUpStrategy messages() default @CleanUpStrategy;
}