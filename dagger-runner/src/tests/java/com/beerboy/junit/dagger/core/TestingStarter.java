package com.beerboy.junit.dagger.core;

import com.beerboy.junit.core.api.Starter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestingStarter implements Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestingStarter.class);

    @Override
    public void start() {
        LOGGER.debug("Starter rule activated");
    }
}
