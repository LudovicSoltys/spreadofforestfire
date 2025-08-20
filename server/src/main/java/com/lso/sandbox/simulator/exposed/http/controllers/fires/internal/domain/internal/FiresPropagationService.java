package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.AvailableBoard;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Context;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FireHandlingProcessor;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.OngoingFiresInventory;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.transaction.Transactional;

/**
 * Implémentation de {@link FirePropagationUseCase}
 */
public class FiresPropagationService implements FirePropagationUseCase {

    private final CurrentBoardSupplier supplier;

    private final OngoingFiresInventory inventory;

    private final FireHandlingProcessor engine;

    private final FiresRegistror registror;

    public FiresPropagationService(CurrentBoardSupplier supplier,
                                   OngoingFiresInventory inventory, FireHandlingProcessor engine, FiresRegistror registror) {
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
