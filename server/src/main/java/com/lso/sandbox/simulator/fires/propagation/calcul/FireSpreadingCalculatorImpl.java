package com.lso.sandbox.simulator.fires.propagation.calcul;

import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implémentation de {@link FireSpreadingCalculator}
 */
public class FireSpreadingCalculatorImpl implements FireSpreadingCalculator {

    private final BoardJpaCrudRepository boards;

    private final CellJpaQueryRepository cells;

    public FireSpreadingCalculatorImpl(BoardJpaCrudRepository boards, CellJpaQueryRepository cells) {
        this.boards = boards;
        this.cells = cells;
    }

    @Override
    public Either<Errors, Iterable<Coordinates>> process(Iterable<Coordinates> values) {

        Bounds bounds = boards.first().map(SimpleBounds::new);

        Set<Coordinates> result = IterableUtils.streamOf(values)
                // détermine toutes les futures positions du feu
                .map(Coordinates::around)
                .flatMap(Collection::stream)
                // évite les doublons
                .distinct()
                // ne garde que les positions qui sont sur le terrain
                .filter(bounds::contains)
                // exclut les coordonnées qui seraient déjà dans le paquet de données source
                .filter(item -> IterableUtils.noneMatch(values, item))
                // ne garde que les positions qui sont encore vertes (c'est-à-dire ni en feu, ni mortes).
                .filter(item -> cells.isAlive(item.getX(), item.getY()))
                .collect(Collectors.toUnmodifiableSet());

        return Either.right(result);
    }

    static class SimpleBounds implements Bounds {

        private final BoardJpaEntity entity;

        public SimpleBounds(BoardJpaEntity entity) {
            this.entity = entity;
        }

        @Override
        public byte yMin() {
            return 0;
        }

        @Override
        public byte yMax() {
            return entity.getHeight();
        }

        @Override
        public byte xMin() {
            return 0;
        }

        @Override
        public byte xMax() {
            return entity.getWidth();
        }
    }
}
