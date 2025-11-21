package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api;

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
