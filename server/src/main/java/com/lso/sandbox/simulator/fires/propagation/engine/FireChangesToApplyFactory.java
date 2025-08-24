package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.add.FireChangeToApply;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.fires.propagation.FireToPropagate;
import com.lso.sandbox.simulator.fires.propagation.NextFire;
import com.lso.sandbox.simulator.shared.util.IterableUtils;

/**
 * Fabrique d'instances de {@link FireChangesToApply}
 */
public class FireChangesToApplyFactory {

    /**
     * Ne doit pas être instancié
     */
    private FireChangesToApplyFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     *
     * @param index un entier positif
     * @param toPropagate doit être non null
     * @param toBurn doit être non null
     * @return une instance immuable de {@link FireChangesToApply}
     */
    static FireChangesToApply of(int index, Iterable<FireToPropagate> toPropagate, Iterable<NextFire> toBurn) {

        Iterable<FireChangeToApply> firesToEnd = IterableUtils.streamOf(toPropagate).map(FireChangeToApplyFactory::of).toList();
        Iterable<FireChangeToApply> firesToStart = IterableUtils.streamOf(toBurn).map(FireChangeToApplyFactory::of).toList();

        return new ImmutableFireChangesToApply(index, firesToEnd, firesToStart);
    }
}
