package com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;

/**
 * Implémentation de {@link BoardRetrievalUseCase}
 */
public class BoardRetrieveService implements BoardRetrievalUseCase {

    private final CurrentBoardSupplier supplier;

    public BoardRetrieveService(CurrentBoardSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void execute(Input input, Output output) {

        supplier.get().then(output::reject, output::accept);
    }
}
