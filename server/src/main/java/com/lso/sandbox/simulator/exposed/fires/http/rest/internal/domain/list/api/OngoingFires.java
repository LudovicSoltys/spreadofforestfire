package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api;

import com.lso.sandbox.simulator.shared.util.IterableUtils;

import java.util.stream.Stream;

/**
 * Paquet de {@link OngoingFire}s
 */
public interface OngoingFires extends Iterable<OngoingFire> {

    int step();

    /**
     *
     * @return true s'il n'y a aucun feu en cours
     */
    boolean isEmpty();

    /**
     *
     * @return un nouveau {@link Stream}
     */
    default Stream<OngoingFire> stream() {
        return IterableUtils.streamOf(this);
    }
}
