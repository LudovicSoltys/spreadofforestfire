package com.lso.sandbox.simulator.fires.list;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier de consulter la position des incendies courants
 */
public interface FireRetrievalUseCase {

    void execute(Input input, Output output);

    interface Input {

    }

    interface Output {

        void accept(Iterable<Coordinates> items);

        void reject(Errors error);
    }
}
