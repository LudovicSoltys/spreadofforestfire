package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api;

import com.lso.sandbox.simulator.shared.Coordinates;
import com.lso.sandbox.simulator.shared.util.Mappable;

/**
 * Incendie en cours
 */
public interface OngoingFire extends Coordinates, Mappable<OngoingFire> {

    /**
     *
     * @return le numéro de l'étape de la simulation associé à l'incendie
     */
    int step();

    int getFlammability();
}
