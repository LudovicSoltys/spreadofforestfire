package com.lso.sandbox.simulator.fires.add.facade;

import com.lso.sandbox.simulator.fires.shared.Coordinates;

/**
 * Modèle du nouvel état d'un incendie, après propagation
 */
public interface CellChangesApplied extends Coordinates {

    boolean isDead();

    boolean isBurning();

    static CellChangesApplied deadCell(int x, int y) {

        return new SimpleCellChangesToApply(x, y, true, false);
    }

    static CellChangesApplied burningCell(int x, int y) {

        return new SimpleCellChangesToApply(x, y, false, true);
    }

    class SimpleCellChangesToApply implements CellChangesApplied {

        private final int x;

        private final int y;

        private boolean isDead;

        private boolean isBurning;

        public SimpleCellChangesToApply(int x, int y, boolean isDead, boolean isBurning) {
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
