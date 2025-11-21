package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FiresToPropagate;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Moteur de calcul de la propagation d'un incendie
 */
public interface FireSpreadingCalculator {

    /**
     *
     * @param values paquet des positions initiales des incendies
     * @return soit le paquet des positions finales de l'incendie, soit les erreurs rencontrées pendant le calcul.
     */
    Either<Errors, FireChangesToApply> process(FiresToPropagate values);
}
