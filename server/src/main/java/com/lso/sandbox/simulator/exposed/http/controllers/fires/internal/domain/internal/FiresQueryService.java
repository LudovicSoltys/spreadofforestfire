package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.OngoingFiresInventory;

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
                .then(output::reject, items -> output.accept(items.getItems()));
    }
}
