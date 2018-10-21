package com.beerboy.junit.dagger.annotation;

import com.beerboy.junit.dagger.di.SwissKnifeModule;

import java.lang.annotation.*;

/**
 * @author faraly
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MainModule {

    Class<? extends SwissKnifeModule> moduleClass();
}