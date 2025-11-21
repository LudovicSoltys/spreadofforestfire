package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api;

import com.lso.sandbox.simulator.infra.facades.board.register.api.CreatedBoard;
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
