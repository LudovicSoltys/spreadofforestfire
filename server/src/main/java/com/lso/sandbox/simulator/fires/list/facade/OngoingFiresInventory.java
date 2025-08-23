package com.lso.sandbox.simulator.fires.list.facade;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface OngoingFiresInventory {

    boolean isEmpty();

    Either<Errors, OngoingFires> findAll();

    interface OngoingFires {

        boolean isEmpty();

        Iterable<Coordinates> getItems();
    }
}
