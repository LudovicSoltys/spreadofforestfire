package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangeToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FireToPropagate;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.NextFire;

import java.util.Objects;

/**
 * Fabrique d'instances de {@link FireChangeToApply}
 */
public class FireChangeToApplyFactory {

    /**
     * Ne doit pas être instancié.
     */
    private FireChangeToApplyFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     *
     * @param data doit être non null
     * @return une instance immuable de {@link FireChangeToApply}
     */
    static FireChangeToApply of(FireToPropagate data) {
        Objects.requireNonNull(data);
        return new ImmutableFireChangeToApply(data.getX(), data.getY(), false, true);
    }

    /**
     *
     * @param data doit être non null
     * @return une instance immuable de {@link FireChangeToApply}
     */
    static FireChangeToApply of(NextFire data) {
        Objects.requireNonNull(data);
        return new ImmutableFireChangeToApply(data.getX(), data.getY(), true, false);
    }

    /**
     * Implémentation basique de {@link FireChangeToApply}
     */
    static class ImmutableFireChangeToApply implements FireChangeToApply {

        private final int x;

        private final int y;

        private final boolean toBurn;

        private final boolean toDeath;

        /**
         *
         * @param x une abscisse, entier positif
         * @param y une ordonnée, entier positif
         * @param toBurn un booléen
         * @param toDeath un booléen
         */
        ImmutableFireChangeToApply(int x, int y, boolean toBurn, boolean toDeath) {
            this.x = x;
            this.y = y;
            this.toBurn = toBurn;
            this.toDeath = toDeath;
        }

        @Override
        public boolean toDeath() {
            return true;
        }

        @Override
        public boolean toBurn() {
            return false;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }
}
