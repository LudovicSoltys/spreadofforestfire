package com.lso.sandbox.simulator.infra.facades.fire.changes.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangeApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;

import java.util.Iterator;

/**
 * Fabrique d'instances de {@link FireChangesApplied}
 */
class FireChangesAppliedFactory {

    /**
     * Ne doit pas être instancié
     */
    private FireChangesAppliedFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     *
     * @param sources les données d'entrée, doit être non null
     * @param values les données résultantes
     * @return une instance basique de {@link FireChangesApplied}
     */
    static FireChangesApplied of(FireChangesToApply sources, Iterable<FireChangeApplied> values) {
        return new FireChangesAppliedImpl(sources, values);
    }

    /**
     * Implémentation simple de {@link FireChangesApplied}
     */
    static class FireChangesAppliedImpl implements FireChangesApplied {

        private final FireChangesToApply sources;

        private final Iterable<FireChangeApplied> values;

        /**
         * Crée une nouvelle instance
         * @param sources doit être non null
         * @param values doit être non null
         */
        FireChangesAppliedImpl(FireChangesToApply sources, Iterable<FireChangeApplied> values) {
            this.sources = sources;
            this.values = values;
        }

        @Override
        public int targetStep() {
            return sources.targetStep();
        }

        @Override
        public Iterator<FireChangeApplied> iterator() {
            return values.iterator();
        }
    }
}
