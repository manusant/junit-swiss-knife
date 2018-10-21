package com.beerboy.junit.dagger.core;

import com.beerboy.junit.core.api.Starter;
import com.beerboy.junit.dagger.di.SwissKnifeModule;
import dagger.Provides;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class TestingDaggerModule extends SwissKnifeModule {

    @Override
    @Provides
    public Set<Starter> starters(){
        return new HashSet<>(Collections.singleton(new TestingStarter()));
    }
}
