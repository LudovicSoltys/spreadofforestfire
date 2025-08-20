package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

public interface Bounds {

    byte yMin();

    byte yMax();

    byte xMin();

    byte xMax();

    default boolean contains(Coordinates item) {
        return item.x() >= this.xMin()
                && item.x() < this.xMax()
                && item.y() >= this.yMin()
                && item.y() < this.yMax();
    }
}
