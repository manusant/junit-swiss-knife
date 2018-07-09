package com.beerboy.junit.guice.annotation;

import com.google.inject.AbstractModule;

import java.lang.annotation.*;

/**
 * @author manusant
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MainModule {

    Class<? extends AbstractModule> moduleClass();
}