package com.lso.sandbox.simulator.fires.add.facade;

import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Optional;

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
    public Either<Errors, Iterable<CellChangesApplied>> saveAll(Iterable<? extends CellChangesToApply> values) {

        BoardJpaEntity board = boards.first();

        Iterable<CellChangesApplied> changedToBurnt = IterableUtils
                .streamOf(values)
                .filter(CellChangesToApply::toBurn)
                .map(value -> inventory.findByXAndY(value.getX(), value.getY()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(entity -> {
                        entity.setBurntAt(board.getLastStep());
                })
                .map(repository::save)
                .map(entity -> CellChangesApplied.burningCell(entity.getX(), entity.getY()))
                .toList();

        Iterable<CellChangesApplied> changedToDead = IterableUtils
                .streamOf(values)
                .filter(CellChangesToApply::toDeath)
                .map(value -> inventory.findByXAndY(value.getX(), value.getY()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(entity -> {
                    entity.setDeadAt(board.getLastStep());
                })
                .map(repository::save)
                .map(entity -> CellChangesApplied.deadCell(entity.getX(), entity.getY()))
                .toList();

        return Either.right(IterableUtils.concat(changedToBurnt, changedToDead));
    }
}
