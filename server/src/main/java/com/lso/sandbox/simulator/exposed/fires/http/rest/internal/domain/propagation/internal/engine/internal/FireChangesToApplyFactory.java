package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangeToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FireToPropagate;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.NextFire;
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
