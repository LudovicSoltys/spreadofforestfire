package com.lso.sandbox.simulator.repositories.facades.fire.changes;

import com.lso.sandbox.simulator.fires.add.FireChangesApplied;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.BoardJpaEntity;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

/**
 * Implémentation de {@link FiresRegistror}. Agrège les opérations de création et de suppression d'incendies.
 */
class BurningCellsUpdatingFacade implements FiresRegistror  {

    private final BoardJpaCrudRepository boardRepository;

    private final FiresRegistror newFiresRegistror;

    private final FiresRegistror deadFiresRegistror;

    BurningCellsUpdatingFacade(BoardJpaCrudRepository boardRepository,
                               FiresRegistror newFiresRegistror, FiresRegistror deadFiresRegistror) {
        this.newFiresRegistror = newFiresRegistror;
        this.deadFiresRegistror = deadFiresRegistror;
        this.boardRepository = boardRepository;
    }

    @Override
    @Transactional
    public Either<Errors, FireChangesApplied> saveAll(FireChangesToApply toApply) {

        Either<Errors, FireChangesApplied> added = newFiresRegistror.saveAll(toApply.firesToAddOnly());
        if (added.isLeft()) {
            return added;
        }

        Either<Errors, FireChangesApplied> removed = deadFiresRegistror.saveAll(toApply.firesToRemoveOnly());
        if (removed.isLeft()) {
            return removed;
        }

        BoardJpaEntity entity = boardRepository.first();
        entity.incrementLastStep();
        boardRepository.save(entity);

        return Either.right(FireChangesAppliedFactory.concat(toApply, added.get(), removed.get()));
    }
}
