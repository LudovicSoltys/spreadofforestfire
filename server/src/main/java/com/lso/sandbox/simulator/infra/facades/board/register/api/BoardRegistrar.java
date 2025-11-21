package com.lso.sandbox.simulator.infra.facades.board.register.api;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardRegistrar {

    Either<Errors, Void> deleteAll();

    Either<Errors, CreatedBoard> save(BoardToCreate values);

}
