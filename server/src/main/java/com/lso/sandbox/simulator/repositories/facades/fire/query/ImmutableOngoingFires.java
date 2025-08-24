package com.lso.sandbox.simulator.repositories.facades.fire.query;

import com.lso.sandbox.simulator.fires.list.OngoingFire;
import com.lso.sandbox.simulator.fires.list.OngoingFires;

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
