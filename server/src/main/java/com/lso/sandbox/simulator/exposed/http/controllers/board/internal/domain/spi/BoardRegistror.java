package com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Rectangle;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface BoardRegistror {

    Either<Errors, Void> deleteAll();

    Either<Errors, CreatedBoard> save(BoardToCreate values);

    interface CreatedBoard {

    }

    interface BoardToCreate extends Rectangle {

    }
}
