package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresRegistror;

/**
 * Implémentation de {@link FireIgnitionUseCase}
 */
public class FiresIgnitionService implements FireIgnitionUseCase {

    private final FiresRegistror registror;

    public FiresIgnitionService(FiresRegistror registror) {
        this.registror = registror;
    }

    @Override
    public void execute(Input input, Output output) {

        registror
                .saveAll(input.targets())
                .then(output::reject, output::accept);
    }
}
