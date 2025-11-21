package com.lso.sandbox.simulator.infra.facades.fire.query;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFire;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;

import java.util.Iterator;
import java.util.List;

/**
 * Implémentation basique de {@link OngoingFires}
 */
class ImmutableOngoingFires implements OngoingFires {

    private final int step;

    private final List<OngoingFire> values;

    /**
     * Crée une nouvelle instance
     * @param step un entier positif
     * @param values doit être non null
     */
    public ImmutableOngoingFires(int step, List<OngoingFire> values) {
        this.step = step;
        this.values = values;
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public int step() {
        return step;
    }

    @Override
    public Iterator<OngoingFire> iterator() {
        return values.iterator();
    }
}
