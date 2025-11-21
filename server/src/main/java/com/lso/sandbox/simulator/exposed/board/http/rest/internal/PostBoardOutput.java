package com.lso.sandbox.simulator.exposed.board.http.rest.internal;

import com.lso.sandbox.simulator.exposed.board.http.rest.api.PostBoardResponse;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.infra.facades.board.register.api.CreatedBoard;
import com.lso.sandbox.simulator.shared.Message;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.List;

class PostBoardOutput implements PostBoardResponse, BoardUpdatingUseCase.Output {
    @Override
    public List<Message> getMessages() {
        return List.of();
    }

    @Override
    public List<Message> getErrors() {
        return List.of();
    }

    @Override
    public void accept(CreatedBoard data) {

    }

    @Override
    public void reject(Errors errors) {

    }
}
