package com.lso.sandbox.simulator.infra.facades.fire.saga.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Dépôt de données des incendies à créer
 */
public interface FiresSagaRegistrar {

    Either<Errors, FireChangesApplied> saveAll(FireChangesToApply values);
}
