package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api;

import com.lso.sandbox.simulator.shared.Coordinates;

/**
 * Modèle du nouvel état d'un incendie, après propagation
 */
public interface FireChangeApplied extends Coordinates {

    boolean isDead();

    boolean isBurning();
}
