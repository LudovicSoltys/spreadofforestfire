package com.lso.sandbox.simulator.infra.engine.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Context;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FireHandlingProcessor;
import com.lso.sandbox.simulator.infra.data.api.*;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.shared.validation.SimpleErrors;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class HandleFireEngine implements FireHandlingProcessor {

    private final BoardJpaCrudRepository boards;

    private final FireSpreadingCalculator calculator;

    private final CellJpaQueryRepository query;

    private final CellJpaCrudRepository cells;

    public HandleFireEngine(BoardJpaCrudRepository boards, FireSpreadingCalculator calculator, CellJpaQueryRepository query, CellJpaCrudRepository cells) {
        this.boards = boards;
        this.calculator = calculator;
        this.query = query;
        this.cells = cells;
    }

    @Override
    @Transactional
    public Either<Errors, Iterable<CellChanges>> process(Iterable<Coordinates> values, Context context) {

        if (boards.isEmpty()) {
            Errors errors = new SimpleErrors();
            errors.reject("board.missing");
            return Either.left(errors);
        }

        BoardJpaEntity board = boards.first();

        Iterable<Coordinates> burningCells = query.findByBurntAtEquals(board.getLastStep()).stream()
                .map(SimpleCoordinates::new)
                .collect(Collectors.toUnmodifiableList());

        if (IterableUtils.isEmpty(burningCells)) {
            return Either.right(List.of());
        }

        Either<Errors, Iterable<Coordinates>> newLocations = calculator.process(burningCells);
        if (newLocations.isLeft()) {
            return Either.left(newLocations.getLeft());
        }

        Iterable<CellChanges> newBurningChanges = markCellsAsBurning(newLocations.get(), context);

        Iterable<CellChanges> newDeadChanges = markCellsAsDead(burningCells, context);

        incrementBoardStep(board, context);

        return Either.right(IterableUtils.concat(newDeadChanges, newBurningChanges));
    }

    private void incrementBoardStep(BoardJpaEntity board, Context context) {

        board.setLastStep(context.targetStep());
        boards.save(board);

    }

    private Iterable<CellChanges> markCellsAsBurning(Iterable<Coordinates> targets, Context context) {

        Iterable<CellJpaEntity> newBurningEntities =  query.findAllByCoordinates(targets).stream()
                .peek(entity -> entity.setBurntAt(context.targetStep()))
                .toList();

        return IterableUtils.streamOf(cells.saveAll(newBurningEntities))
                .map(entity -> CellChanges.burningCell(entity.getX(), entity.getY()))
                .toList();
    }

    private Iterable<CellChanges> markCellsAsDead(Iterable<Coordinates> targets, Context context) {

        Iterable<CellJpaEntity> newDeadEntities = query.findAllByCoordinates(targets).stream()
                .peek(entity -> entity.setDeadAt(context.targetStep()))
                .toList();

        return IterableUtils.streamOf(cells.saveAll(newDeadEntities))
                .map(entity -> CellChanges.deadCell(entity.getX(), entity.getY()))
                .toList();
    }

    record SimpleCoordinates(CellJpaEntity entity) implements Coordinates {

        @Override
            public int x() {
                return entity.getX();
            }

            @Override
            public int y() {
                return entity.getY();
            }
        }
}
