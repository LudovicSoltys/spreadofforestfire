package com.lso.sandbox.simulator.infra.facade.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.AvailableBoard;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.ImmutableAvailableBoard;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

public class BoardReadingFacade implements CurrentBoardSupplier {

    private final BoardJpaCrudRepository boards;

    private final CellsStatsJpaQueryRepository stats;

    public BoardReadingFacade(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        this.boards = boards;
        this.stats = stats;
    }

    @Override
    public Either<Errors, AvailableBoard> get() {

        return boards
                .first()
                .map(entity -> ImmutableAvailableBoard.builder()
                        .width(entity.getWidth())
                        .height(entity.getHeight())
                        .currentStep(entity.getLastStep())
                        .greenCount(stats.countByBurntAtIsNull())
                        .burningCount(stats.countByBurntAtIsNotNullAndDeadAtIsNull())
                        .deadCount(stats.countByDeadAtIsNotNull())
                        .build())
                .map(Either::right);
    }
}
