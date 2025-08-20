package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface OngoingFiresInventory {

    Either<Errors, OngoingFires> findAll();

    interface OngoingFires {

        boolean isEmpty();

        Iterable<Coordinates> getItems();
    }
}
