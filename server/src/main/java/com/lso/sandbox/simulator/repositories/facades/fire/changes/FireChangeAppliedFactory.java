package com.lso.sandbox.simulator.repositories.facades.fire.changes;

import com.lso.sandbox.simulator.fires.add.FireChangeApplied;

/**
 * Fabrique d'instances de {@link FireChangeApplied}
 */
class FireChangeAppliedFactory {

    /**
     * Ne doit pas être instancié
     */
    private FireChangeAppliedFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     *
     * @param x abscisse, entier positif
     * @param y ordinnée, entier positif
     * @return une instance de {@link FireChangeApplied} pour une position d'incendie à marquer comme "morte".
     */
    static FireChangeApplied deadFire(int x, int y) {

        return new SimpleFireChangeToApply(x, y, true, false);
    }

    /**
     *
     * @param x abscisse, entier positif
     * @param y ordinnée, entier positif
     * @return une instance de {@link FireChangeApplied} pour une position d'incendie à marquer comme "à démarrer".
     */
    static FireChangeApplied burningFire(int x, int y) {

        return new SimpleFireChangeToApply(x, y, false, true);
    }

    /**
     * Implémentation basique de {@link FireChangeApplied}
     */
    static class SimpleFireChangeToApply implements FireChangeApplied {

        private final int x;

        private final int y;

        private boolean isDead;

        private boolean isBurning;

        SimpleFireChangeToApply(int x, int y, boolean isDead, boolean isBurning) {
            this.x = x;
            this.y = y;
            this.isDead = isDead;
            this.isBurning = isBurning;
        }

        @Override
        public boolean isDead() {
            return isDead;
        }

        @Override
        public boolean isBurning() {
            return isBurning;
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
