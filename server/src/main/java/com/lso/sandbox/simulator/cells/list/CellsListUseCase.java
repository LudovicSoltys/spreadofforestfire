package com.lso.sandbox.simulator.cells.list;

import com.lso.sandbox.simulator.shared.validation.Errors;

import static com.lso.sandbox.simulator.cells.list.facade.AllCellsSupplier.AllCells;

public interface CellsListUseCase {

    void execute(Input input, Output output);

    interface Input {

    }

    interface Output {

        void accept(AllCells data);

        void reject(Errors errors);
    }
}
