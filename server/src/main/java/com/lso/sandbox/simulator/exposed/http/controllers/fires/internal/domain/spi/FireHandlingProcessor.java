package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FireHandlingProcessor {

    Either<Errors, Iterable<CellChanges>> process(Iterable<Coordinates> values, Context context);
}
