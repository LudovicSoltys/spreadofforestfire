package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.fires.propagation.FiresToPropagate;
import com.lso.sandbox.simulator.fires.propagation.NextFire;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
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

        Set<NextFire> result = IterableUtils.streamOf(values.around())
                // ne garde que les positions qui sont encore vertes (c'est-à-dire ni en feu, ni mortes).
                .filter(item -> inventory.isAlive(item.getX(), item.getY()))
                .collect(Collectors.toUnmodifiableSet());

        return Either.right(FireChangesToApplyFactory.of(values.step(), values, result));
    }

}
