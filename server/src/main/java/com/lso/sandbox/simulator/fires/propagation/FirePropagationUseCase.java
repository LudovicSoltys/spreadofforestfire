package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.fires.add.facade.CellChangesApplied;
import com.lso.sandbox.simulator.shared.util.Mappable;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier d'appliquer la propagation d'incendies
 */
public interface FirePropagationUseCase {

    void execute(Input input, Output output);

    interface Input extends Mappable<Input> {

    }

    interface Output {

        void accept(Iterable<CellChangesApplied> values);

        void reject(Errors error);
    }
}
