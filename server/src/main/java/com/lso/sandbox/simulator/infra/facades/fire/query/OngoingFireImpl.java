package com.lso.sandbox.simulator.infra.facades.fire.query;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFire;
import com.lso.sandbox.simulator.infra.repositories.CellJpaEntity;

/**
 * Implémentation de {@link OngoingFire}
 */
class OngoingFireImpl implements OngoingFire {

    private final int step;

    private final CellJpaEntity entity;

    public OngoingFireImpl(int step, CellJpaEntity entity) {
        this.step = step;
        this.entity = entity;
    }

    @Override
    public int step() {
        return step;
    }

    @Override
    public int getX() {
        return entity.getX();
    }

    @Override
    public int getY() {
        return entity.getY();
    }

    @Override
    public int getFlammability() {
        return entity.getFlammability();
    }
}
