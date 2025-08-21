package com.lso.sandbox.simulator.board.supplier;

import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;

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
