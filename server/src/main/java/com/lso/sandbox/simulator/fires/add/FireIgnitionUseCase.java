package com.lso.sandbox.simulator.fires.add;

import com.lso.sandbox.simulator.fires.add.facade.CellChangesApplied;
import com.lso.sandbox.simulator.fires.add.facade.CellChangesToApply;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.validation.Errors;

/**
 * Définition du besoin métier de créer de nouveaux incendies
 */
public interface FireIgnitionUseCase {

    void execute(Input input, Output output);

    interface Input {
        Iterable<CellChangesToApply> targets();
    }

    interface Output {

        Iterable<Message> getMessages();

        void accept(Iterable<CellChangesApplied> values);

        void reject(Errors error);
    }
}
