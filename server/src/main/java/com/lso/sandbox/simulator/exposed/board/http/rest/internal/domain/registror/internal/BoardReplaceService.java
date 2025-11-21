package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.internal;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.Rectangle;
import com.lso.sandbox.simulator.infra.facades.board.register.api.BoardRegistrar;
import com.lso.sandbox.simulator.infra.facades.board.register.api.BoardToCreate;
import jakarta.transaction.Transactional;

public class BoardReplaceService implements BoardUpdatingUseCase {

    private final BoardRegistrar registror;

    public BoardReplaceService(BoardRegistrar registror) {
        this.registror = registror;
    }

    @Override
    @Transactional
    public void execute(Input input, Output output) {

        this.registror.deleteAll();

        this.registror
                .save(input.map(BoardToCreateImpl::new))
                .then(output::reject, output::accept);
    }

    static class BoardToCreateImpl implements BoardToCreate {

        private final Rectangle data;

        public BoardToCreateImpl(Input value) {
            this.data = value.getAttributes();
        }

        @Override
        public byte getWidth() {
            return data.getWidth();
        }

        @Override
        public byte getHeight() {
            return data.getHeight();
        }
    }
}
