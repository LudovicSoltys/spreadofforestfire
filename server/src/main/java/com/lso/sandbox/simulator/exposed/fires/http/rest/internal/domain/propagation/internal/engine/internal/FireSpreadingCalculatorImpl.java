package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FireToPropagate;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FiresToPropagate;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.NextFire;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.api.FireSpreadingCalculator;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implémentation de {@link FireSpreadingCalculator}
 */
public class FireSpreadingCalculatorImpl implements FireSpreadingCalculator {

    private final CellJpaQueryRepository inventory;

    public FireSpreadingCalculatorImpl(CellJpaQueryRepository cells) {
        this.inventory = cells;
    }

    @Override
    public Either<Errors, FireChangesToApply> process(FiresToPropagate values) {

        Set<NextFire> result = values.stream()
                // il y a une certaine probabilité que le feu se propage...
                .filter(FireToPropagate::canPropagate)
                // ... à chacune des 4 positions adjacentes...
                .flatMap(item -> IterableUtils.streamOf(item.around()))
                // ... qui seraient encore vertes (c'est-à-dire ni en feu, ni en cendres).
                .filter(item -> inventory.isAlive(item.getX(), item.getY()))
                .collect(Collectors.toUnmodifiableSet());

        return Either.right(FireChangesToApplyFactory.of(values.step(), values, result));
    }

}
