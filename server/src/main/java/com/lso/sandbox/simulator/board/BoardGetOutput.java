package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.board.supplier.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BoardGetOutput implements BoardGetResponse, BoardRetrievalUseCase.Output {

    private AvailableBoard data = AvailableBoard.empty();

    private boolean exists = false;

    private List<Message> messages = new ArrayList<>();

    private List<Message> errors = new ArrayList<>();

    @Override
    public boolean isPresent() {
        return exists;
    }

    @Override
    public long getWidth() {
        return data.getWidth();
    }

    @Override
    public long getHeight() {
        return data.getHeight();
    }

    @Override
    public int getCurrentStep() {
        return data.currentStep();
    }

    @Override
    public List<Message> getMessages() {
        return List.of();
    }

    @Override
    public List<Message> getErrors() {
        return List.of();
    }

    @Override
    public void accept(AvailableBoard item) {
        Objects.requireNonNull(item);
        data = item;
        exists = data.getHeight() > 0 && data.getWidth() > 0;

        if (!exists) {
            messages.add(Message.of("board.missing"));
        }
    }

    @Override
    public void reject(Errors errors) {

        errors.getAllErrors().forEach(error -> {
            this.errors.add(Message.of(error.getCode(), error.getArguments()));
        });
    }
}
