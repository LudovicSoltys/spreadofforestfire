package com.lso.sandbox.simulator.infra.facades.fire.changes.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangeApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Optional;

/**
 * Implémentation de {@link FiresRegistrar} dédiée à enregistrer la suppression d'incendies
 */
public class BurningCellsRemovalFacade implements FiresRegistrar {

    private final CellJpaCrudRepository repository;

    private final CellJpaQueryRepository inventory;

    public BurningCellsRemovalFacade(CellJpaCrudRepository repository, CellJpaQueryRepository inventory) {
        this.repository = repository;
        this.inventory = inventory;
    }

    @Override
    public Either<Errors, FireChangesApplied> saveAll(FireChangesToApply toApply)  {

        Iterable<FireChangeApplied> changesApplied = toApply.firesToRemoveOnly().stream()
                .map(value -> inventory.findByXAndY(value.getX(), value.getY()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(entity -> entity.setDeadAt(toApply.targetStep()))
                .map(repository::save)
                .map(entity -> FireChangeAppliedFactory.deadFire(entity.getX(), entity.getY()))
                .toList();

        return Either.right(FireChangesAppliedFactory.of(toApply, changesApplied));
    }
}
