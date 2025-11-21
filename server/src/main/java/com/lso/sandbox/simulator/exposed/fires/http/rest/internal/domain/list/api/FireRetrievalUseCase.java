package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api;

import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier de consulter la position des incendies courants
 */
public interface FireRetrievalUseCase {

    void execute(Input input, Output output);

    interface Input {

    }

    interface Output {

        void accept(OngoingFires values);

        void reject(Errors error);
    }
}
