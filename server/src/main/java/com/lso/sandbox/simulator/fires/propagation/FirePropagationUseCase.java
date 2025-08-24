package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.fires.add.FireChangeApplied;
import com.lso.sandbox.simulator.shared.util.Mappable;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier d'appliquer la propagation d'incendies
 */
public interface FirePropagationUseCase {

    void execute(Input input, Output output);

    /**
     * Données d'entrée
     */
    interface Input extends Mappable<Input> {

    }

    /**
     * Données de sortie
     */
    interface Output {

        void accept(Iterable<FireChangeApplied> values);

        void reject(Errors error);
    }
}
