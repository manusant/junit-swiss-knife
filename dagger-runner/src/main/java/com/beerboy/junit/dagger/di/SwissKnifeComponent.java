package com.beerboy.junit.dagger.di;

import com.beerboy.junit.core.api.Cleaner;
import com.beerboy.junit.core.api.Loader;
import com.beerboy.junit.core.api.Starter;
import dagger.Component;

import java.util.Set;

@Component(modules = SwissKnifeModule.class)
public interface SwissKnifeComponent {

    Set<Starter> getStarters();

    Set<Loader> getLoaders();

    Set<Cleaner> getCleaners();
 }
