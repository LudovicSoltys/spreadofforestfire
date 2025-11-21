package com.lso.sandbox.simulator.infra.facades.fire.saga.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.saga.api.FiresSagaRegistrar;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

/**
 * Implémentation de {@link FiresRegistrar}. Agrège les opérations de création et de suppression d'incendies.
 */
class BurningCellsUpdatingFacade implements FiresSagaRegistrar {

    private final BoardJpaCrudRepository boardRepository;

    private final FiresRegistrar newFiresRegistrar;

    private final FiresRegistrar deadFiresRegistrar;

    BurningCellsUpdatingFacade(BoardJpaCrudRepository boardRepository,
                               FiresRegistrar newFiresRegistrar, FiresRegistrar deadFiresRegistrar) {
        this.newFiresRegistrar = newFiresRegistrar;
        this.deadFiresRegistrar = deadFiresRegistrar;
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional
    public Either<Errors, FireChangesApplied> saveAll(FireChangesToApply toApply) {

        Either<Errors, FireChangesApplied> added = newFiresRegistrar.saveAll(toApply.firesToAddOnly());
        if (added.isLeft()) {
            return added;
        }

        Either<Errors, FireChangesApplied> removed = deadFiresRegistrar.saveAll(toApply.firesToRemoveOnly());
        if (removed.isLeft()) {
            return removed;
        }

        BoardJpaEntity entity = boardRepository.first();
        entity.incrementLastStep();
        boardRepository.save(entity);

        return Either.right(FireSagaChangesAppliedFactory.concat(toApply, added.get(), removed.get()));
    }
}
