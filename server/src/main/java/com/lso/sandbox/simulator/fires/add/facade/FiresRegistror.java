package com.lso.sandbox.simulator.fires.add.facade;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public interface FiresRegistror {

    Either<Errors, Iterable<CellChangesApplied>> saveAll(Iterable<? extends CellChangesToApply> values);
}
