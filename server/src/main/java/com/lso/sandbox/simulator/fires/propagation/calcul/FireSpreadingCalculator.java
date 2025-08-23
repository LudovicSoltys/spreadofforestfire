package com.lso.sandbox.simulator.fires.propagation.calcul;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
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
    Either<Errors, Iterable<Coordinates>> process(Iterable<Coordinates> values);
}
