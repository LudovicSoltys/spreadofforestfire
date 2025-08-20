package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

public interface CellChanges extends Coordinates {

    boolean isDead();

    boolean isBurning();

    static CellChanges deadCell(int x, int y) {

        return new SimpleCellChanges(x, y, true, false);
    }

    static CellChanges burningCell(int x, int y) {

        return new SimpleCellChanges(x, y, false, true);
    }

    class SimpleCellChanges implements CellChanges {

        private final int x;

        private final int y;

        private boolean isDead;

        private boolean isBurning;

        public SimpleCellChanges(int x, int y, boolean isDead, boolean isBurning) {
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
        public int x() {
            return x;
        }

        @Override
        public int y() {
            return y;
        }
    }
}
