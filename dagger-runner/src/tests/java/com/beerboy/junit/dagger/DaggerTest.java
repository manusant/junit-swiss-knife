package com.beerboy.junit.dagger;

import com.beerboy.junit.core.annotation.CleanUp;
import com.beerboy.junit.dagger.annotation.MainModule;
import com.beerboy.junit.dagger.core.TestingDaggerModule;
import com.beerboy.junit.dagger.runner.DaggerSwissKnifeRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DaggerSwissKnifeRunner.class)
@MainModule(moduleClass = TestingDaggerModule.class)
@CleanUp()
public class DaggerTest {

    @Test
    public void testDagger(){

    }
}
