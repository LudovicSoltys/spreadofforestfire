package com.lso.sandbox.simulator.infra.facades.board.read.internal;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.AvailableBoard;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.read.api.OneSingleBoard;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.shared.validation.SimpleErrors;

public class BoardReadingFacade implements CurrentBoardSupplier {

    private final BoardJpaCrudRepository boards;

    public BoardReadingFacade(BoardJpaCrudRepository boards) {
        this.boards = boards;
    }

    @Override
    public Either<Errors, OneSingleBoard> get() {

        if (boards.isEmpty()) {
            Errors errors = new SimpleErrors();
            errors.reject("board.missing");
            return Either.left(errors);
        }

        return boards
                .first()
                .map(ImmutableOneSingleBoard::new)
                .map(Either::right);
    }

    /**
     * Une implémentation immuable de {@link AvailableBoard}
     */
    static class ImmutableOneSingleBoard implements OneSingleBoard {

        private final BoardJpaEntity entity;

        /**
         * Crée une nouvelle instance
         * @param entity doit être non null
         */
        ImmutableOneSingleBoard(BoardJpaEntity entity) {
            this.entity = entity;
        }

        @Override
        public long getWidth() {
            return entity.getWidth();
        }

        @Override
        public long getHeight() {
            return entity.getHeight();
        }

        @Override
        public int currentStep() {
            return entity.getLastStep();
        }
    }
}
