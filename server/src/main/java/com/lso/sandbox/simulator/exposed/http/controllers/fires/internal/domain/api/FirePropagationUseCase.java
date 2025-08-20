package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.shared.util.Mappable;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FirePropagationUseCase {

    void execute(Input input, Output output);

    interface Input extends Mappable<Input> {

    }

    interface Output {

        void accept(Iterable<CellChanges> values);

        void reject(Errors error);
    }
}
