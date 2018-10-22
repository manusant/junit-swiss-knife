package com.beerboy.junit.dagger.di;

import com.beerboy.junit.core.annotation.Messaging;
import com.beerboy.junit.core.api.*;
import dagger.Component;

import java.util.Set;

@Component(modules = SwissKnifeModule.class)
public interface SwissKnifeComponent {

    Set<Starter> getStarters();

    Set<Loader> getLoaders();

    Set<MessagingCleaner> getMessagingCleaners();

    Set<StorageCleaner> getStorageCleaners();
 }
