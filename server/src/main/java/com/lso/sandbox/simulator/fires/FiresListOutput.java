package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.ArrayList;
import java.util.List;

class FiresListOutput implements FireListResponse, FireRetrievalUseCase.Output {

    private Iterable<Coordinates> content = List.of();

    private final List<Message> messages = new ArrayList<>();

    private final List<Message> errors = new ArrayList<>();

    @Override
    public Iterable<Coordinates> getContent() {
        return content;
    }

    @Override
    public long getTotalElements() {
        return IterableUtils.count(content);
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
    public void accept(Iterable<Coordinates> values) {

        this.content = values;

        if (IterableUtils.isEmpty(values)) {
            messages.add(Message.of("fires.empty"));
        }
    }

    @Override
    public void reject(Errors errors) {

        errors
                .getAllErrors()
                .forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
    }
}
