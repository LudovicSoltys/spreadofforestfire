package com.lso.sandbox.simulator.board.registror;

import com.lso.sandbox.simulator.board.registror.facade.BoardRegistror;
import com.lso.sandbox.simulator.board.registror.facade.BoardToCreate;
import jakarta.transaction.Transactional;

public class BoardReplaceService implements BoardUpdatingUseCase {

    private final BoardRegistror registror;

    public BoardReplaceService(BoardRegistror registror) {
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
