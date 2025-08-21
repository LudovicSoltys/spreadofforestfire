package com.lso.sandbox.simulator.fires.list;

import com.lso.sandbox.simulator.fires.list.facade.OngoingFiresInventory;

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
