package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api;

import com.lso.sandbox.simulator.shared.Coordinates;

/**
 * Modèle de l'état d'un incendie, avant propagation
 */
public interface FireChangeToApply extends Coordinates {

    boolean toDeath();

    boolean toBurn();
}
