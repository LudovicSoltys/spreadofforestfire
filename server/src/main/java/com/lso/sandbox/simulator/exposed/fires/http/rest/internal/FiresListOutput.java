package com.lso.sandbox.simulator.exposed.fires.http.rest.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FireListResponse;
import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FireListResponseItem;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.ArrayList;
import java.util.List;

class FiresListOutput implements FireListResponse, FireRetrievalUseCase.Output {

    private List<FireListResponseItem> content;

    private final List<Message> messages = new ArrayList<>();

    private final List<Message> errors = new ArrayList<>();

    @Override
    public Iterable<FireListResponseItem> getContent() {
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
    public void accept(OngoingFires values) {

        this.content = values.stream().map(FireListResponseItem::of).toList();

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
