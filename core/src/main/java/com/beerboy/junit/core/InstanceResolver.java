package com.beerboy.junit.core;

/**
 * @author manusant
 */
@FunctionalInterface
public interface InstanceResolver {

    <T> T resolve(Class<T> clazz);
}
