package com.beerboy.junit.core.rule;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

@FunctionalInterface
public interface SwissKnifeRule {

    void apply(Statement base, Description description);
}