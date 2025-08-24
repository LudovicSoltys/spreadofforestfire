package com.lso.sandbox.simulator.repositories.facades.fire.changes;

import com.lso.sandbox.simulator.fires.add.FireChangeApplied;
import com.lso.sandbox.simulator.fires.add.FireChangesApplied;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.repositories.data.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Optional;

/**
 * Implémentation de {@link FiresRegistror} dédiée à enregistrer la suppression d'incendies
 */
class BurningCellsRemovalFacade implements FiresRegistror {

    private final CellJpaCrudRepository repository;

    private final CellJpaQueryRepository inventory;

    BurningCellsRemovalFacade(CellJpaCrudRepository repository, CellJpaQueryRepository inventory) {
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
