package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.add.FireChangeToApply;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.shared.util.IterableUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Implémentation basique de {@link FireChangesToApply}
 */
class ImmutableFireChangesToApply implements FireChangesToApply {

    private final int index;

    private final Iterable<FireChangeToApply> initialValues;

    private final Iterable<FireChangeToApply> newValues;

    ImmutableFireChangesToApply(int index, Iterable<FireChangeToApply> initialValues, Iterable<FireChangeToApply> newValues) {
        this.index = index;
        this.initialValues = initialValues;
        this.newValues = newValues;
    }

    @Override
    public int targetStep() {
        return index;
    }

    @Override
    public FireChangesToApply firesToAddOnly() {
        return new ImmutableFireChangesToApply(index, List.of(), newValues);
    }

    @Override
    public FireChangesToApply firesToRemoveOnly() {
        return new ImmutableFireChangesToApply(index, initialValues, List.of());
    }

    @Override
    public Iterator<FireChangeToApply> iterator() {
        return IterableUtils.concat(initialValues, newValues).iterator();
    }
}
