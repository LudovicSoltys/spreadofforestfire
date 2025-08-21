package com.lso.sandbox.simulator.board.supplier.facade;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.function.Supplier;

public interface CurrentBoardSupplier extends Supplier<Either<Errors, AvailableBoard>> {
}
