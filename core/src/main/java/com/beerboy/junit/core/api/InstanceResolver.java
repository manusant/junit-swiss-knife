package com.beerboy.junit.core.api;

/**
 * @author manusant
 */
@FunctionalInterface
public interface InstanceResolver {

    <T> T resolve(Class<T> clazz);
}
