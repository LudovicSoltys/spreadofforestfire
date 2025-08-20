package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FireRetrievalUseCase {

    void execute(Input input, Output output);

    interface Input {

    }

    interface Output {

        void accept(Iterable<Coordinates> items);

        void reject(Errors error);
    }
}
