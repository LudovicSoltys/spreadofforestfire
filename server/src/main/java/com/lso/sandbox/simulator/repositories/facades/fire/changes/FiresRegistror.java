package com.lso.sandbox.simulator.repositories.facades.fire.changes;

import com.lso.sandbox.simulator.fires.add.FireChangesApplied;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Dépôt de données des incendies à créer
 */
public interface FiresRegistror {

    Either<Errors, FireChangesApplied> saveAll(FireChangesToApply values);
}
