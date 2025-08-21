package com.lso.sandbox.simulator.board.supplier.facade;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.shared.validation.SimpleErrors;

public class BoardReadingFacade implements CurrentBoardSupplier {

    private final BoardJpaCrudRepository boards;

    private final CellsStatsJpaQueryRepository stats;

    public BoardReadingFacade(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        this.boards = boards;
        this.stats = stats;
    }

    @Override
    public Either<Errors, AvailableBoard> get() {

        if (boards.isEmpty()) {
            Errors errors = new SimpleErrors();
            errors.reject("board.missing");
            return Either.left(errors);
        }

        return boards
                .first()
                .map(entity -> new SimpleAvailableBoard(entity,
                        stats.countByBurntAtIsNull(),
                        stats.countByBurntAtIsNotNullAndDeadAtIsNull(),
                        stats.countByDeadAtIsNotNull()))
                .map(Either::right);
    }

    class SimpleAvailableBoard implements AvailableBoard {

        private final BoardJpaEntity entity;

        private final Integer countAlive;

        private final Integer countBurning;

        private final Integer countDead;

        public SimpleAvailableBoard(BoardJpaEntity entity, Integer countAlive, Integer countBurning, Integer countDead) {
            this.entity = entity;
            this.countAlive = countAlive;
            this.countBurning = countBurning;
            this.countDead = countDead;
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

        @Override
        public int greenCount() {
            return countAlive;
        }

        @Override
        public int burningCount() {
            return countBurning;
        }

        @Override
        public int deadCount() {
            return countDead;
        }
    }
}
