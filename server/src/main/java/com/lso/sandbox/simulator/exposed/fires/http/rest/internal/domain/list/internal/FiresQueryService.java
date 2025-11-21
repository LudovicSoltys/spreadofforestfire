package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.infra.facades.fire.query.OngoingFiresInventory;

/**
 * Implémentation de {@link FireRetrievalUseCase}
 */
public class FiresQueryService implements FireRetrievalUseCase {

    private final OngoingFiresInventory fires;

    public FiresQueryService(OngoingFiresInventory fires) {
        this.fires = fires;
    }

    @Override
    public void execute(Input input, Output output) {

        fires.findAll()
                .then(output::reject, output::accept);
    }
}
