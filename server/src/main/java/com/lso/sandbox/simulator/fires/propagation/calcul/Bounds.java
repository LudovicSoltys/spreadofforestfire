package com.lso.sandbox.simulator.fires.propagation.calcul;

import com.lso.sandbox.simulator.fires.shared.Coordinates;

public interface Bounds {

    byte yMin();

    byte yMax();

    byte xMin();

    byte xMax();

    default boolean contains(Coordinates item) {
        return item.getX() >= this.xMin()
                && item.getX() < this.xMax()
                && item.getY() >= this.yMin()
                && item.getY() < this.yMax();
    }
}
