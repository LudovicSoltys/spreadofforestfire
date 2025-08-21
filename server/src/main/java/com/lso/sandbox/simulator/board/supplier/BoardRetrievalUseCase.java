package com.lso.sandbox.simulator.board.supplier;

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
