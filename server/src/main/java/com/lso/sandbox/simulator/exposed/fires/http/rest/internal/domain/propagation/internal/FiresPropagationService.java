package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FiresToPropagate;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.api.FireSpreadingCalculator;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.read.api.OneSingleBoard;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.query.OngoingFiresInventory;
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

    private final FiresRegistrar firesRegistrar;

    public FiresPropagationService(CurrentBoardSupplier supplier, OngoingFiresInventory inventory,
                                   FireSpreadingCalculator engine, FiresRegistrar firesRegistrar) {
        this.supplier = supplier;
        this.inventory = inventory;
        this.engine = engine;
        this.firesRegistrar = firesRegistrar;
    }

    @Override
    @Transactional
    public void execute(Input input, Output output) {

        Either<Errors, OneSingleBoard> maybeBoard = supplier.get();
        if (maybeBoard.isLeft()) {
            output.reject(maybeBoard.getLeft());
            return;
        }

        if (inventory.isEmpty()) {
            output.accept(List.of());
            return;
        }

        OneSingleBoard board = maybeBoard.get();
        inventory
                .findAll()
                .flatMap(values -> FiresToPropagate.of(board, values).map(Either::right))
                .flatMap(engine::process)
                .flatMap(firesRegistrar::saveAll)
                .then(output::reject, output::accept);
    }
}
