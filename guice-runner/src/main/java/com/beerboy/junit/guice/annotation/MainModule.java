package com.beerboy.junit.guice.annotation;

import com.beerboy.junit.guice.di.MainModuleIT;

import java.lang.annotation.*;

/**
 * @author manusant
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MainModule {

    Class<? extends MainModuleIT> moduleClass();
}