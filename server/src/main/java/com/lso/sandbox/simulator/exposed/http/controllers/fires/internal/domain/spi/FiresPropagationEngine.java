package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FiresPropagationEngine {

    Either<Errors, Iterable<Coordinates>> process(Iterable<Coordinates> values);
}
