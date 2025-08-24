package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.shared.util.IterableUtils;

import java.util.stream.Stream;

/**
 * Positions des incendies suivants
 */
public interface FireChangesToApply extends Iterable<FireChangeToApply> {

    int targetStep();

    default int size() {
        return (int) stream().count();
    }

    default Stream<FireChangeToApply> stream() {
        return IterableUtils.streamOf(this);
    }

    FireChangesToApply firesToAddOnly();

    FireChangesToApply firesToRemoveOnly();
}
