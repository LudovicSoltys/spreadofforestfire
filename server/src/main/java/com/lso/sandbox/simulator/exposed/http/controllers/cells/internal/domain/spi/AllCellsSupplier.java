package com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.spi;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.function.Supplier;

import static com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.spi.AllCellsSupplier.AllCells;

public interface AllCellsSupplier extends Supplier<Either<Errors, AllCells>> {

    interface AllCells extends Iterable<Cell> {

    }

    interface Cell extends Coordinates {

        boolean isAlive();

        boolean isBurning();

        boolean isDead();
    }
}
