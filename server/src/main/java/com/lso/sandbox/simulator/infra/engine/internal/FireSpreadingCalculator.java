package com.lso.sandbox.simulator.infra.engine.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Bounds;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresPropagationEngine;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaEntity;
import com.lso.sandbox.simulator.infra.data.api.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.shared.validation.SimpleErrors;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FireSpreadingCalculator implements FiresPropagationEngine {

    private final BoardJpaCrudRepository boards;

    private final CellJpaQueryRepository cells;

    public FireSpreadingCalculator(BoardJpaCrudRepository boards, CellJpaQueryRepository cells) {
        this.boards = boards;
        this.cells = cells;
    }

    @Override
    public Either<Errors, Iterable<Coordinates>> process(Iterable<Coordinates> values) {

        if (boards.isEmpty()) {
            Errors errors = new SimpleErrors();
            errors.reject("board.missing");
            return Either.left(errors);
        }

        if (IterableUtils.isEmpty(values)) {
            return Either.right(List.of());
        }

        Bounds bounds = boards.first().map(SimpleBounds::new);

        Set<Coordinates> result = IterableUtils.streamOf(values)
                // détermine toutes les futures positions du feu
                .map(Coordinates::around)
                .flatMap(Collection::stream)
                // évite les doublons
                .distinct()
                // ne garde que les positions qui sont sur le terrain
                .filter(bounds::contains)
                // ne garde que les positions qui sont encore vertes (c'est-à-dire ni en feu, ni mortes).
                .filter(item -> cells.isAlive(item.x(), item.y()))
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
