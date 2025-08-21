package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.add.facade.CellChangesToApply;

class PostFiresInput implements FireIgnitionUseCase.Input {

    private final FiresAddRequest params;

    public PostFiresInput(FiresAddRequest params) {
        this.params = params;
    }

    @Override
    public Iterable<CellChangesToApply> targets() {
        return params.getTargets().stream()
                .map(value -> CellChangesToApply.burningCell(value.getX(), value.getY()))
                .toList();
    }
}
