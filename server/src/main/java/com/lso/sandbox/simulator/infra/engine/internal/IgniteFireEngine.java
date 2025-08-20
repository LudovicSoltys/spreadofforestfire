package com.lso.sandbox.simulator.infra.engine.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Context;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FireHandlingProcessor;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaEntity;
import com.lso.sandbox.simulator.infra.data.api.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IgniteFireEngine implements FireHandlingProcessor {

    private final CellJpaQueryRepository query;

    private final CellJpaCrudRepository cells;

    public IgniteFireEngine(CellJpaQueryRepository query, CellJpaCrudRepository cells) {
        this.query = query;
        this.cells = cells;
    }

    @Override
    public Either<Errors, Iterable<CellChanges>> process(Iterable<Coordinates> values, Context context) {

        if (IterableUtils.isEmpty(values)) {
            return Either.right(List.of());
        }

        Iterable<CellJpaEntity> modifiedEntities = markCellsAsBurning(values, context);

        Iterable<CellJpaEntity> savedEntities = cells.saveAll(modifiedEntities);

        return result(savedEntities);
    }


    private Iterable<CellJpaEntity> markCellsAsBurning(Iterable<Coordinates> targets, Context context) {
        return StreamSupport.stream(targets.spliterator(), false)
                .map(value -> query.findByXAndY(value.x(), value.y()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(entity -> entity.setBurntAt(context.targetStep()))
                .toList();
    }

    private static Either<Errors, Iterable<CellChanges>> result(Iterable<CellJpaEntity> entities) {
        Iterable<CellChanges> result = StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> CellChanges.burningCell(entity.getX(), entity.getY()))
                .collect(Collectors.toUnmodifiableSet());

        return Either.right(result);
    }
}
