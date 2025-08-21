package com.lso.sandbox.simulator.board.registror.facade;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardRegistror {

    Either<Errors, Void> deleteAll();

    Either<Errors, CreatedBoard> save(BoardToCreate values);

}
