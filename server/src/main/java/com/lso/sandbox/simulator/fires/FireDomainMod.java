package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.board.supplier.facade.BoardSupplierMod;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.add.FiresIgnitionService;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.list.FiresQueryService;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.fires.propagation.FiresPropagationService;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingCalculator;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingEngineMod;
import com.lso.sandbox.simulator.repositories.facades.fire.changes.FiresRegistror;
import com.lso.sandbox.simulator.repositories.facades.fire.query.FiresFacadeMod;
import com.lso.sandbox.simulator.repositories.facades.fire.query.OngoingFiresInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
                                                   @Autowired FireSpreadingCalculator engine,
                                                   @Autowired @Qualifier("firesUpdatingRegistror") FiresRegistror firesRegistror) {

        return new FiresPropagationService(supplier, inventory, engine, firesRegistror);
    }

    @Bean
    public FireIgnitionUseCase igniteUseCase(@Autowired @Qualifier("firesAdditionRegistror") FiresRegistror registror) {

        return new FiresIgnitionService(registror);
    }

    @Bean
    public FireRetrievalUseCase retrievalUseCase(@Autowired OngoingFiresInventory fires) {
        return new FiresQueryService(fires);
    }
}
