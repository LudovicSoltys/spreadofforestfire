package com.lso.sandbox.simulator.infra.facades.fire.query;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFire;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.infra.repositories.CellJpaEntity;

import java.util.List;

/**
 * Fabrique d'instances de {@link OngoingFires}
 */
public class OngoingFiresFactory {

    /**
     * Ne doit pas être instancié
     */
    private OngoingFiresFactory() {
        throw new IllegalStateException("Factory class");
    }

    /**
     *
     * @param board doit être non null
     * @param entities doit être non null
     * @return une nouvelle instance de {@link OngoingFires}
     */
    static OngoingFires of(BoardJpaEntity board, List<CellJpaEntity> entities) {
        int step = board.getLastStep();
        List<OngoingFire> values = entities.stream()
                .map(entity -> (OngoingFire) new OngoingFireImpl(step, entity))
                .toList();
        return of(step, values);
    }

    /**
     *
     * @param index un entier positif
     * @param values doit être non null
     * @return une nouvelle instance de {@link OngoingFires}
     */
    public static OngoingFires of(int index, List<OngoingFire> values) {
        return new ImmutableOngoingFires(index, values);
    }
}
