package com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.api.CellsListUseCase;
import com.lso.sandbox.simulator.infra.facades.cells.list.api.AllCellsSupplier;

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
