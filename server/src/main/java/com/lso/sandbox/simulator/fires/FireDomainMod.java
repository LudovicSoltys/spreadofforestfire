package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.board.supplier.facade.BoardSupplierMod;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.add.FiresIgnitionService;
import com.lso.sandbox.simulator.fires.add.facade.FiresRegistror;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.list.FiresQueryService;
import com.lso.sandbox.simulator.fires.list.facade.FiresFacadeMod;
import com.lso.sandbox.simulator.fires.list.facade.OngoingFiresInventory;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.fires.propagation.FiresPropagationService;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingEngineMod;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingProcessor;
import com.lso.sandbox.simulator.fires.validation.*;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Module dédié au domaine métier de la gestion des incendies : consultation, création et propagation.
 */
@Configuration
@Import( {BoardSupplierMod.class, FireSpreadingEngineMod.class, FiresFacadeMod.class})
public class FireDomainMod {

    @Bean
    public FirePropagationUseCase propagateUseCase(@Autowired CurrentBoardSupplier supplier,
                                                   @Autowired OngoingFiresInventory inventory,
                                                   @Autowired FireSpreadingProcessor engine,
                                                   @Autowired FiresRegistror registror) {

        return new FiresPropagationService(supplier, inventory, engine, registror);
    }

    @Bean
    public FireIgnitionUseCase igniteUseCase(@Autowired FiresRegistror registror) {

        return new FiresIgnitionService(registror);
    }

    @Bean
    public FireRetrievalUseCase retrievalUseCase(@Autowired OngoingFiresInventory fires) {
        return new FiresQueryService(fires);
    }

    @Bean
    public ConstraintValidator<InBoundConstraint, FiresAddRequest.TargetItem> firesAddRequestInBoundConstraint(
            @Autowired BoardJpaCrudRepository boards
    ) {
        return new InBoundConstraintValidator(boards);
    }

    @Bean
    public ConstraintValidator<BoundsDefinedConstraint, FiresChangeRequest> firesChangeRequestBoundsDefinedConstraintValidator(
            @Autowired BoardJpaCrudRepository boards
    ) {
        return new BoundsDefinedConstraintValidator(boards);
    }

    @Bean
    public ConstraintValidator<StillAliveConstraint, FiresAddRequest.TargetItem> firesAddRequestStillAliveConstraintValidator(
            @Autowired CellJpaQueryRepository cells
    ) {
        return new StillAliveConstraintValidator(cells);
    }
}
