package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.add.facade.CellChangesToApply;
import com.lso.sandbox.simulator.fires.propagation.Context;
import com.lso.sandbox.simulator.fires.propagation.calcul.FireSpreadingCalculator;
import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.repositories.*;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implémentation de {@link FireSpreadingProcessor}
 * <p>
 * À partir des coordonnées initiales des incendies :
 * * calcule les coordonnées suivantes
 * * marque les cellules correspondantes comme "en feu"
 * * marque les cellules initiales comme "mortes" (c'est-à-dire brûlées)
 */
public class FireSpreadingEngine implements FireSpreadingProcessor {

    private final BoardJpaCrudRepository boards;

    private final FireSpreadingCalculator calculator;

    private final CellJpaQueryRepository query;

    public FireSpreadingEngine(BoardJpaCrudRepository boards, FireSpreadingCalculator calculator, CellJpaQueryRepository query) {
        this.boards = boards;
        this.calculator = calculator;
        this.query = query;
    }

    @Override
    @Transactional
    public Either<Errors, Iterable<CellChangesToApply>> process(Iterable<Coordinates> values, Context context) {

        BoardJpaEntity board = boards.first();

        Iterable<Coordinates> initialBurningCells = query.findByBurntAtEquals(board.getLastStep()).stream()
                .map(SimpleCoordinates::new)
                .collect(Collectors.toUnmodifiableList());

        if (IterableUtils.isEmpty(initialBurningCells)) {
            return Either.right(List.of());
        }

        Either<Errors, Iterable<Coordinates>> newLocations = calculator.process(initialBurningCells);
        if (newLocations.isLeft()) {
            return Either.left(newLocations.getLeft());
        }

        Iterable<CellChangesToApply> newBurningChanges = markCellsAsBurning(newLocations.get(), context);

        Iterable<CellChangesToApply> newDeadChanges = markCellsAsDead(initialBurningCells, context);

        return Either.right(IterableUtils.concat(newDeadChanges, newBurningChanges));
    }

    private Iterable<CellChangesToApply> markCellsAsBurning(Iterable<Coordinates> targets, Context context) {

        return query.findAllByCoordinates(targets).stream()
                .peek(entity -> entity.setBurntAt(context.targetStep()))
                .map(entity -> CellChangesToApply.burningCell(entity.getX(), entity.getY()))
                .toList();
    }

    private Iterable<CellChangesToApply> markCellsAsDead(Iterable<Coordinates> targets, Context context) {

        return query.findAllByCoordinates(targets).stream()
                .peek(entity -> entity.setDeadAt(context.targetStep()))
                .map(entity -> CellChangesToApply.deadCell(entity.getX(), entity.getY()))
                .toList();
    }

    record SimpleCoordinates(CellJpaEntity entity) implements Coordinates {

        @Override
            public int getX() {
                return entity.getX();
            }

            @Override
            public int getY() {
                return entity.getY();
            }
        }
}
