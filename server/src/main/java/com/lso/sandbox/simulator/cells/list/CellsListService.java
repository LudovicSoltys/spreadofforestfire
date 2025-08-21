package com.lso.sandbox.simulator.cells.list;

import com.lso.sandbox.simulator.cells.list.facade.AllCellsSupplier;

public class CellsListService implements CellsListUseCase {

    private final AllCellsSupplier supplier;

    public CellsListService(AllCellsSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void execute(Input input, Output output) {

        supplier.get().then(output::reject, output::accept);
    }
}
