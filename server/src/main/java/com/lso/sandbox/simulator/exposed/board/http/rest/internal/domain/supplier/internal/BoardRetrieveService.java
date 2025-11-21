package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.internal;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.AvailableBoard;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.read.api.OneSingleBoard;
import com.lso.sandbox.simulator.infra.facades.board.stats.BoardStatsRequestor;
import com.lso.sandbox.simulator.infra.facades.board.stats.CurrentBoardMeasures;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.util.Tuple2;

import java.util.function.Function;

/**
 * Implémentation de {@link BoardRetrievalUseCase}
 */
public class BoardRetrieveService implements BoardRetrievalUseCase {

    private final CurrentBoardSupplier supplier;

    private final BoardStatsRequestor stats;

    public BoardRetrieveService(CurrentBoardSupplier supplier, BoardStatsRequestor stats) {
        this.supplier = supplier;
        this.stats = stats;
    }

    @Override
    public void execute(Input input, Output output) {

        Either
                .merge(supplier.get(), stats.findAll())
                .flatMap(((Function<Tuple2<OneSingleBoard, CurrentBoardMeasures>, AvailableBoard>) ImmutableAvailableBoard::new).andThen(Either::right))
                .then(output::reject, output::accept);
    }

    static class ImmutableAvailableBoard implements AvailableBoard {

        private final OneSingleBoard data;

        private final CurrentBoardMeasures values;


        public ImmutableAvailableBoard(Tuple2<OneSingleBoard, CurrentBoardMeasures> data) {
            this(data._1(), data._2());
        }

        public ImmutableAvailableBoard(OneSingleBoard data, CurrentBoardMeasures values) {
            this.data = data;
            this.values = values;
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
        public int currentStep() {
            return data.currentStep();
        }

        @Override
        public int burningCount() {
            return 0;
        }

        @Override
        public int deadCount() {
            return 0;
        }
    }
}
