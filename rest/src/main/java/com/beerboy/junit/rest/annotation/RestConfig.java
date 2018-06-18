package com.beerboy.junit.rest.annotation;

import java.lang.annotation.*;

/**
 * @author mfilho
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RestConfig {

    String baseURI();

    String basePath() default "/";
}
