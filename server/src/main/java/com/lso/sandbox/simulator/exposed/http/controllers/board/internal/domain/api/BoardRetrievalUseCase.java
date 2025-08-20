package com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.AvailableBoard;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardRetrievalUseCase {

    void execute(Input input, Output output);

    interface Input {

    }

    interface Output {

        void accept(AvailableBoard item);

        void reject(Errors errors);
    }
}
