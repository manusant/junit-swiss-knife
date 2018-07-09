package com.beerboy.junit.core.rule;

import com.beerboy.junit.core.api.Loader;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Set;

/**
 * @author manusant
 */
public class LoaderRule implements TestRule {

    private final Set<Loader> loaders;

    public LoaderRule(Set<Loader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (loaders != null) {
                    loaders.forEach(Loader::load);
                }
                base.evaluate();
            }
        };
    }
}