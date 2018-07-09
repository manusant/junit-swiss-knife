package com.beerboy.junit.core.rule;

import com.beerboy.junit.core.api.Starter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Set;

/**
 * @author manusant
 */
public class StarterRule implements TestRule {

    private final Set<Starter> starters;

    public StarterRule(Set<Starter> starters) {
        this.starters = starters;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (starters != null) {
                    starters.forEach(Starter::start);
                }
                base.evaluate();
            }
        };
    }
}