package com.beerboy.junit.dagger.di;

import com.beerboy.junit.core.api.Cleaner;
import com.beerboy.junit.core.api.Loader;
import com.beerboy.junit.core.api.Starter;
import dagger.Module;
import dagger.Provides;

import java.util.HashSet;
import java.util.Set;

@Module
public class SwissKnifeModule {

    @Provides
    public Set<Starter> starters(){
        return new HashSet<>();
    }

    @Provides
    public Set<Loader> loaders(){
        return new HashSet<>();
    }

    @Provides
    public Set<Cleaner> cleaners(){
        return new HashSet<>();
    }
}
