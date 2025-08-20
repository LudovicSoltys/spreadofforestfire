package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.shared.Message;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FireIgnitionUseCase {

    void execute(Input input, Output output);

    interface Input {
        Iterable<Coordinates> targets();
    }

    interface Output {

        Iterable<Message> getMessages();

        void accept(Iterable<CellChanges> values);

        void reject(Errors error);
    }
}
