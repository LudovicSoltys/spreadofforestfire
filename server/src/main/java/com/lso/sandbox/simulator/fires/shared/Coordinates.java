package com.lso.sandbox.simulator.fires.shared;

import java.util.List;
import java.util.Objects;

public interface Coordinates {

    int getX();

    int getY();

    static Coordinates of(int x, int y) {
        return new SimpleCoordinates(x, y);
    }

    default Coordinates up() {
        return new SimpleCoordinates(this.getX(), this.getY() - 1);
    }

    default Coordinates bottom() {
        return new SimpleCoordinates(this.getX(), this.getY() + 1);
    }

    default Coordinates left() {
        return new SimpleCoordinates(this.getX() - 1, this.getY());
    }

    default Coordinates right() {
        return new SimpleCoordinates(this.getX() + 1, this.getY());
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

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            SimpleCoordinates that = (SimpleCoordinates) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
