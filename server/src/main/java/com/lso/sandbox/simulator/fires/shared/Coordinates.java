package com.lso.sandbox.simulator.fires.shared;

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
}
