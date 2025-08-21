package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingProcessor;
import com.lso.sandbox.simulator.fires.add.facade.FiresRegistror;
import com.lso.sandbox.simulator.fires.list.facade.OngoingFiresInventory;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

/**
 * Implémentation de {@link FirePropagationUseCase}
 */
public class FiresPropagationService implements FirePropagationUseCase {

    private final CurrentBoardSupplier supplier;

    private final OngoingFiresInventory inventory;

    private final FireSpreadingProcessor engine;

    private final FiresRegistror registror;

    public FiresPropagationService(CurrentBoardSupplier supplier,
                                   OngoingFiresInventory inventory, FireSpreadingProcessor engine, FiresRegistror registror) {
        this.supplier = supplier;
        this.inventory = inventory;
        this.engine = engine;
        this.registror = registror;
    }

    @Override
    @Transactional
    public void execute(Input input, Output output) {

        Either<Errors, AvailableBoard> board = supplier.get();
        if (board.isLeft()) {
            output.reject(board.getLeft());
            return;
        }

        Context context = Context.next(board.get().currentStep());

        inventory
                .findAll()
                .flatMap(fires -> engine.process(fires.getItems(), context))
                .flatMap(registror::saveAll)
                .then(output::reject, output::accept);
    }
}
