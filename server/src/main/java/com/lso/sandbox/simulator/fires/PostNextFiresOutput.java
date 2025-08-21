package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.facade.CellChangesApplied;
import com.lso.sandbox.simulator.fires.add.facade.CellChangesToApply;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.ArrayList;
import java.util.List;

class PostNextFiresOutput implements FiresChangeResponse, FirePropagationUseCase.Output {

    private List<PostFiresOutputChanges> changes;

    private final List<Message> messages = new ArrayList<>();

    private final List<Message> errors = new ArrayList<>();

    @Override
    public Iterable<PostFiresOutputChanges> getChanges() {
        return changes;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public List<Message> getErrors() {
        return errors;
    }

    @Override
    public void accept(Iterable<CellChangesApplied> values) {

        this.changes = IterableUtils.streamOf(values).map(PostFiresOutputChanges::new).toList();

        if (IterableUtils.isEmpty(values)) {
            messages.add(Message.of("propagation.empty"));
        }
    }

    @Override
    public void reject(Errors errors) {
        errors
                .getAllErrors()
                .forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
    }
}
