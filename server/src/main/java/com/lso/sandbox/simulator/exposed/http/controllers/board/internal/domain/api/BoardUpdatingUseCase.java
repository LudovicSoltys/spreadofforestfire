package com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.BoardRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Rectangle;
import com.lso.sandbox.simulator.shared.util.Mappable;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardUpdatingUseCase {

    void execute(Input input, Output output);

    interface Input extends Mappable<Input> {

        Rectangle getAttributes();
    }

    interface Output {

        void accept(BoardRegistror.CreatedBoard data);

        void reject(Errors errors);
    }
}
