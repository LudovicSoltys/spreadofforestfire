package com.lso.sandbox.simulator.fires.list.facade;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.List;

/**
 * Implémentation de {@link OngoingFiresInventory}
 */
public class BurningCellsReadingFacade implements OngoingFiresInventory {

    private final BoardJpaCrudRepository boards;

    private final CellJpaQueryRepository inventory;

    public BurningCellsReadingFacade(BoardJpaCrudRepository boards, CellJpaQueryRepository inventory) {
        this.boards = boards;
        this.inventory = inventory;
    }

    @Override
    public Either<Errors, OngoingFires> findAll() {

        BoardJpaEntity board = boards.first();

        List<Coordinates> values = IterableUtils
                .streamOf(inventory.findByBurntAtEquals(board.getLastStep()))
                .map(entity -> Coordinates.of(entity.getX(), entity.getY()))
                .toList();

        return Either.right(new OngoingFiresImpl(values));
    }



    static class OngoingFiresImpl implements OngoingFires {

        private final List<Coordinates> values;

        public OngoingFiresImpl(List<Coordinates> values) {
            this.values = values;
        }

        @Override
        public boolean isEmpty() {
            return values.isEmpty();
        }

        @Override
        public Iterable<Coordinates> getItems() {
            return values;
        }
    }
}
