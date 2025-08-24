package com.lso.sandbox.simulator.repositories.facades.fire.query;

import com.lso.sandbox.simulator.fires.list.OngoingFires;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface OngoingFiresInventory {

    boolean isEmpty();

    Either<Errors, OngoingFires> findAll();
}
