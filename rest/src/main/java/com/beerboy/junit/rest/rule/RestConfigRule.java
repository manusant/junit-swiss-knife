package com.beerboy.junit.rest.rule;

import com.beerboy.junit.rest.annotation.RestConfig;
import io.restassured.RestAssured;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author mfilho
 */
public class RestConfigRule extends TestWatcher {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                RestConfig config = description.getAnnotation(RestConfig.class);
                if (config != null) {
                    RestAssured.baseURI = config.baseURI();
                    RestAssured.basePath = config.basePath();
                }
                base.evaluate();
            }
        };
    }
}
