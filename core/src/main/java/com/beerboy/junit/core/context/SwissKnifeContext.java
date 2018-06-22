package com.beerboy.junit.core.context;

import com.beerboy.junit.core.api.Loader;
import sun.misc.Cleaner;

import java.util.Set;

/**
 * @author manusant
 */
public class SwissKnifeContext {

    private Set<Cleaner> cleaners;
    private Set<Loader> loaders;

    public Set<Cleaner> getCleaners() {
        return cleaners;
    }

    public void setCleaners(Set<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public Set<Loader> getLoaders() {
        return loaders;
    }

    public void setLoaders(Set<Loader> loaders) {
        this.loaders = loaders;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Set<Cleaner> cleaners;
        private Set<Loader> loaders;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withCleaners(Set<Cleaner> cleaners) {
            this.cleaners = cleaners;
            return this;
        }

        public Builder withLoaders(Set<Loader> loaders) {
            this.loaders = loaders;
            return this;
        }

        public SwissKnifeContext build() {
            SwissKnifeContext swissKnifeContext = new SwissKnifeContext();
            swissKnifeContext.setCleaners(cleaners);
            swissKnifeContext.setLoaders(loaders);
            return swissKnifeContext;
        }
    }
}
