package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.fires.add.facade.FiresRegistror;

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
