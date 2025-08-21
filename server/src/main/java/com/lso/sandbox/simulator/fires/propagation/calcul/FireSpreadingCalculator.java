package com.lso.sandbox.simulator.fires.propagation.calcul;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FireSpreadingCalculator {

    Either<Errors, Iterable<Coordinates>> process(Iterable<Coordinates> values);
}
