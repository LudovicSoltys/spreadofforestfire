package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.fires.list.OngoingFire;
import com.lso.sandbox.simulator.fires.shared.Coordinates;

import java.util.List;

/**
 * Position d'un feu à propager
 */
public interface FireToPropagate extends Coordinates {

    int step();

    Iterable<NextFire> around();

    static FireToPropagate of(int index, OngoingFire data) {
        return new FireToPropagateImpl(index, data);
    }

    /**
     * Implémentation simple de {@link OngoingFire}
     */
    class FireToPropagateImpl implements FireToPropagate {

        private final int step;

        private final OngoingFire data;

        public FireToPropagateImpl(int step, OngoingFire data) {
            this.step = step;
            this.data = data;
        }

        @Override
        public int step() {
            return step;
        }

        @Override
        public int getX() {
            return data.getX();
        }

        @Override
        public int getY() {
            return data.getY();
        }

        /**
         *
         * @return les coordonnées de la position au-dessus de la position courante
         */
        NextFire up() {
            return NextFire.of(step, this.getX(), this.getY() - 1);
        }

        /**
         *
         * @return les coordonnées de la position en-dessous de la position courante
         */
        NextFire bottom() {
            return NextFire.of(step, this.getX(), this.getY() + 1);
        }

        /**
         *
         * @return les coordonnées de la position à gauche de la position courante
         */
        NextFire left() {
            return NextFire.of(step, this.getX() - 1, this.getY());
        }

        /**
         *
         * @return les coordonnées de la position à droite de la position courante
         */
        NextFire right() {
            return NextFire.of(step, this.getX() + 1, this.getY());
        }

        /**
         *
         * @return les coordonnées des positions autour de la position courante
         */
        @Override
        public Iterable<NextFire> around() {
            return List.of(
                    this.left(),
                    this.right(),
                    this.up(),
                    this.bottom());
        }
    }
}
