package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api;

import com.lso.sandbox.simulator.shared.Coordinates;

/**
 * Coordonées d'un futur incendie
 */
public interface NextFire extends Coordinates {

    int step();

    /**
     * Fabrique
     *
     * @param x une valeur entière
     * @param y une valeur entière
     * @return une instance de {@link Coordinates}
     */
    static NextFire of(int step, int x, int y) {
        return new SimpleNextFire(step, x, y);
    }

    /**
     * Implémentation basique de {@link NextFire}
     */
    class SimpleNextFire implements NextFire {

        private final int step;

        private final int x;

        private final int y;

        public SimpleNextFire(int step, int x, int y) {
            this.step = step;
            this.x = x;
            this.y = y;
        }

        public int step() {
            return step;
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
