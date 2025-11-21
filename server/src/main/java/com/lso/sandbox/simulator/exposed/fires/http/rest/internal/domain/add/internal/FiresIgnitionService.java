package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;

/**
 * Implémentation de {@link FireIgnitionUseCase}
 */
public class FiresIgnitionService implements FireIgnitionUseCase {

    private final FiresRegistrar registror;

    public FiresIgnitionService(FiresRegistrar registror) {
        this.registror = registror;
    }

    @Override
    public void execute(Input input, Output output) {

        registror
                .saveAll(input.targets())
                .then(output::reject, output::accept);
    }
}
