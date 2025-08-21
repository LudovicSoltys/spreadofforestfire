package com.lso.sandbox.simulator.board.registror;

import com.lso.sandbox.simulator.board.registror.facade.CreatedBoard;
import com.lso.sandbox.simulator.shared.util.Mappable;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardUpdatingUseCase {

    void execute(Input input, Output output);

    interface Input extends Mappable<Input> {

        Rectangle getAttributes();
    }

    interface Output {

        void accept(CreatedBoard data);

        void reject(Errors errors);
    }
}
