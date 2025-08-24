package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.fires.shared.Coordinates;

/**
 * Modèle de l'état d'un incendie, avant propagation
 */
public interface FireChangeToApply extends Coordinates {

    boolean toDeath();

    boolean toBurn();
}
