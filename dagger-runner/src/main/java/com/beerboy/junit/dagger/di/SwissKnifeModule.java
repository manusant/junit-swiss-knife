package com.beerboy.junit.dagger.di;

import com.beerboy.junit.core.annotation.Messaging;
import com.beerboy.junit.core.annotation.Storage;
import com.beerboy.junit.core.api.*;
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
    public Set<MessagingCleaner> storageCleaners(){
        return new HashSet<>();
    }

    @Provides
    public Set<StorageCleaner> messagingCleaners(){
        return new HashSet<>();
    }
}
