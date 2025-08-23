package com.lso.sandbox.simulator.fires.shared;

import java.util.List;
import java.util.Objects;

/**
 * Définition d'une position sur le plateau de la simulation
 */
public interface Coordinates {

    /**
     *
     * @return une abscisse
     */
    int getX();

    /**
     *
     * @return une ordonnée
     */
    int getY();

    /**
     * Fabrique
     * @param x une valeur entière
     * @param y une valeur entière
     * @return une instance de {@link Coordinates}
     */
    static Coordinates of(int x, int y) {
        return new SimpleCoordinates(x, y);
    }

    /**
     *
     * @return les coordonnées de la position au-dessus de la position courante
     */
    default Coordinates up() {
        return new SimpleCoordinates(this.getX(), this.getY() - 1);
    }

    /**
     *
     * @return les coordonnées de la position en-dessous de la position courante
     */
    default Coordinates bottom() {
        return new SimpleCoordinates(this.getX(), this.getY() + 1);
    }

    /**
     *
     * @return les coordonnées de la position à gauche de la position courante
     */
    default Coordinates left() {
        return new SimpleCoordinates(this.getX() - 1, this.getY());
    }

    /**
     *
     * @return les coordonnées de la position à droite de la position courante
     */
    default Coordinates right() {
        return new SimpleCoordinates(this.getX() + 1, this.getY());
    }

    /**
     *
     * @return les coordonnées des positions autour de la position courante
     */
    default List<Coordinates> around() {
        return List.of(
                this.left(),
                this.right(),
                this.up(),
                this.bottom());
    }

    /**
     * Implémentation basique de {@link Coordinates}
     */
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
