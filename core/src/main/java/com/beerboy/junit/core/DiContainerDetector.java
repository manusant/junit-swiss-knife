package com.beerboy.junit.core;

import com.beerboy.scanner.ClassScanner;

import java.io.IOException;

public class DiContainerDetector {

    enum DiContainer {
        GUICE("com.google.inject", "Guice"),
        DAGGER_2("", ""),
        SPRING("", "");

        private String packageName;
        private String mainClass;

        DiContainer(String packageName, String mainClass) {
            this.packageName = packageName;
            this.mainClass = mainClass;
        }

        public String getMainClass() {
            return mainClass;
        }

        public String getPackageName() {
            return packageName;
        }
    }

    public DiContainer detectContainer() throws IOException {
        for (DiContainer container :DiContainer.values()) {
            long found = ClassScanner.scan(ClassLoader.getSystemClassLoader(), container.getPackageName())
                    .stream()
                    .filter(clazz -> clazz.getSimpleName().equals(container.getMainClass()))
                    .count();
            if (found){

            }

        }
    }
}
