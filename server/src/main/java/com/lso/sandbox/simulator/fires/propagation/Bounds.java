package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.fires.shared.Coordinates;

/**
 * Limites du périmètre de la simulation
 */
public interface Bounds {

    byte yMin();

    byte yMax();

    byte xMin();

    byte xMax();

    /**
     *
     * @param item doit être non null
     * @return true si les données sont comprises dans le périmètre. false, sinon.
     */
    default boolean contains(Coordinates item) {
        return item.getX() >= this.xMin()
                && item.getX() < this.xMax()
                && item.getY() >= this.yMin()
                && item.getY() < this.yMax();
    }

    /**
     * Fabrique utilitaire
     * @param board doit être non null
     * @return une nouvelle instance de {@link Bounds}
     */
    static Bounds of(AvailableBoard board) {
        return new SimpleBounds(board);
    }

    /**
     * Implémentation simple de {@link Bounds}
     */
    class SimpleBounds implements Bounds {

        private final AvailableBoard data;

        public SimpleBounds(AvailableBoard data) {
            this.data = data;
        }

        @Override
        public byte yMin() {
            return 0;
        }

        @Override
        public byte yMax() {
            return (byte) data.getHeight();
        }

        @Override
        public byte xMin() {
            return 0;
        }

        @Override
        public byte xMax() {
            return (byte) data.getWidth();
        }
    }
}
