package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

import java.util.List;

public interface Coordinates {

    int x();

    int y();

    static Coordinates of(int x, int y) {
        return new SimpleCoordinates(x, y);
    }

    default Coordinates up() {
        return new SimpleCoordinates(this.x(), this.y() - 1);
    }

    default Coordinates bottom() {
        return new SimpleCoordinates(this.x(), this.y() + 1);
    }

    default Coordinates left() {
        return new SimpleCoordinates(this.x() - 1, this.y());
    }

    default Coordinates right() {
        return new SimpleCoordinates(this.x() + 1, this.y());
    }

    default List<Coordinates> around() {
        return List.of(
                this.left(),
                this.right(),
                this.up(),
                this.bottom());
    }

    class SimpleCoordinates implements Coordinates {

        private final int x;

        private final int y;

        public SimpleCoordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }
    }
}
