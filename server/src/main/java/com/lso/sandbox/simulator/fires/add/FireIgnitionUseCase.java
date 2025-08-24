package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier de créer de nouveaux incendies
 */
public interface FireIgnitionUseCase {

    void execute(Input input, Output output);

    /**
     * Données d'entrée
     */
    interface Input {
        FireChangesToApply targets();
    }

    /**
     * Données de sortie
     */
    interface Output {

        void accept(FireChangesApplied values);

        void reject(Errors error);
    }
}
