package com.lso.sandbox.simulator.repositories.facades.fire.query;

import com.lso.sandbox.simulator.fires.list.OngoingFires;
import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.data.CellJpaEntity;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.List;

/**
 * Implémentation de {@link OngoingFiresInventory}
 */
class BurningCellsReadingFacade implements OngoingFiresInventory {

    private final BoardJpaCrudRepository boards;

    private final CellJpaQueryRepository inventory;

    BurningCellsReadingFacade(BoardJpaCrudRepository boards, CellJpaQueryRepository inventory) {
        this.boards = boards;
        this.inventory = inventory;
    }

    @Override
    public boolean isEmpty() {

        BoardJpaEntity board = boards.first();

        return inventory.findByBurntAtEquals(board.getLastStep()).isEmpty();
    }

    @Override
    public Either<Errors, OngoingFires> findAll() {

        BoardJpaEntity board = boards.first();

        List<CellJpaEntity> values = IterableUtils
                .streamOf(inventory.findByBurntAtEquals(board.getLastStep()))
                .toList();

        return Either.right(OngoingFiresFactory.of(board, values));
    }
}
