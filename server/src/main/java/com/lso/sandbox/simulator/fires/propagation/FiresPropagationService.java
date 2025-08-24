package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingCalculator;
import com.lso.sandbox.simulator.repositories.facades.fire.changes.FiresRegistror;
import com.lso.sandbox.simulator.repositories.facades.fire.query.OngoingFiresInventory;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Implémentation de {@link FirePropagationUseCase}
 */
public class FiresPropagationService implements FirePropagationUseCase {

    private final CurrentBoardSupplier supplier;

    private final OngoingFiresInventory inventory;

    private final FireSpreadingCalculator engine;

    private final FiresRegistror firesRegistror;

    public FiresPropagationService(CurrentBoardSupplier supplier, OngoingFiresInventory inventory,
                                   FireSpreadingCalculator engine, FiresRegistror firesRegistror) {
        this.supplier = supplier;
        this.inventory = inventory;
        this.engine = engine;
        this.firesRegistror = firesRegistror;
    }

    @Override
    @Transactional
    public void execute(Input input, Output output) {

        Either<Errors, AvailableBoard> maybeBoard = supplier.get();
        if (maybeBoard.isLeft()) {
            output.reject(maybeBoard.getLeft());
            return;
        }

        if (inventory.isEmpty()) {
            output.accept(List.of());
            return;
        }

        AvailableBoard board = maybeBoard.get();
        inventory
                .findAll()
                .flatMap(values -> {
                    FiresToPropagate data = FiresToPropagate.of(board, values);
                    return engine.process(data);
                })
                .flatMap(firesRegistror::saveAll)
                .then(output::reject, output::accept);
    }
}
