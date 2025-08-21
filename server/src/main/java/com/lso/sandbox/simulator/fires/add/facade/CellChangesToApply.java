package com.lso.sandbox.simulator.fires.add.facade;

import com.lso.sandbox.simulator.fires.shared.Coordinates;

public interface CellChangesToApply extends Coordinates {

    boolean toDeath();

    boolean toBurn();

    static CellChangesToApply deadCell(int x, int y) {

        return new SimpleCellChangesToApply(x, y, true, false);
    }

    static CellChangesToApply burningCell(int x, int y) {

        return new SimpleCellChangesToApply(x, y, false, true);
    }

    class SimpleCellChangesToApply implements CellChangesToApply {

        private final int x;

        private final int y;

        private boolean toDeath;

        private boolean toBurn;

        public SimpleCellChangesToApply(int x, int y, boolean toDeath, boolean toBurn) {
            this.x = x;
            this.y = y;
            this.toDeath = toDeath;
            this.toBurn = toBurn;
        }

        @Override
        public boolean toDeath() {
            return toDeath;
        }

        @Override
        public boolean toBurn() {
            return toBurn;
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
