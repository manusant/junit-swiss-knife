package com.beerboy.junit.guice.di;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

public abstract class MainModuleIT extends AbstractModule {

    public abstract void onBind(final Injector inject);
}
