package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.fires.shared.Coordinates;

/**
 * Modèle du nouvel état d'un incendie, après propagation
 */
public interface FireChangeApplied extends Coordinates {

    boolean isDead();

    boolean isBurning();
}
