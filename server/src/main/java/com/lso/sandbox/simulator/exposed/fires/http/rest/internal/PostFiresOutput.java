package com.lso.sandbox.simulator.exposed.fires.http.rest.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresChangeResponse;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.ArrayList;
import java.util.List;

class PostFiresOutput implements FiresChangeResponse, FireIgnitionUseCase.Output {

    private List<PostFiresOutputChanges> changes;

    private final List<Message> messages = new ArrayList<>();

    private final List<Message> errors = new ArrayList<>();

    @Override
    public List<PostFiresOutputChanges> getChanges() {
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
    public void accept(FireChangesApplied values) {

        this.changes = IterableUtils.streamOf(values).map(PostFiresOutputChanges::new).toList();

        if (IterableUtils.isEmpty(values)) {
            messages.add(Message.of("fires.empty"));
        }
    }

    @Override
    public void reject(Errors errors) {

        errors.getAllErrors().forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
    }

}
