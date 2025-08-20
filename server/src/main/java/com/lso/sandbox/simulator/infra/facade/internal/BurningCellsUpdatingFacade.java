package com.lso.sandbox.simulator.infra.facade.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresRegistror;
import com.lso.sandbox.simulator.infra.data.api.*;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Implémentation de {@link FiresRegistror}
 */
public class BurningCellsUpdatingFacade implements FiresRegistror {

    private final BoardJpaCrudRepository boards;

    private final CellJpaCrudRepository repository;

    private final CellJpaQueryRepository inventory;

    public BurningCellsUpdatingFacade(BoardJpaCrudRepository boards, CellJpaCrudRepository repository, CellJpaQueryRepository inventory) {
        this.boards = boards;
        this.repository = repository;
        this.inventory = inventory;
    }

    @Override
    public Either<Errors, Iterable<CellChanges>> saveAll(Iterable<? extends Coordinates> values) {

        BoardJpaEntity board = boards.first();

        Iterable<CellJpaEntity> entities = IterableUtils
                .streamOf(inventory.findAllByCoordinates((Iterable<Coordinates>) values))
                .peek(entity -> entity.setBurntAt(board.getLastStep()))
                .toList();

        Iterable<CellChanges> changes = IterableUtils
                .streamOf(repository.saveAll(entities))
                .map(entity -> CellChanges.burningCell(entity.getX(), entity.getY()))
                .toList();

        return Either.right(changes);
    }
}
