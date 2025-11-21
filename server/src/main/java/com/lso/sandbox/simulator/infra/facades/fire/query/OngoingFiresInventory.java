package com.lso.sandbox.simulator.infra.facades.fire.query;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface OngoingFiresInventory {

    boolean isEmpty();

    Either<Errors, OngoingFires> findAll();
}
