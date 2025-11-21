package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.internal.FiresIgnitionService;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.internal.FiresQueryService;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.FiresPropagationService;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.FireSpreadingEngineMod;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.api.FireSpreadingCalculator;
import com.lso.sandbox.simulator.infra.facades.board.read.BoardSupplierMod;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.query.FiresFacadeMod;
import com.lso.sandbox.simulator.infra.facades.fire.query.OngoingFiresInventory;
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
                                                   @Autowired @Qualifier("firesUpdatingRegistror") FiresRegistrar firesRegistrar) {

        return new FiresPropagationService(supplier, inventory, engine, firesRegistrar);
    }

    @Bean
    public FireIgnitionUseCase igniteUseCase(@Autowired @Qualifier("firesAdditionRegistror") FiresRegistrar registror) {

        return new FiresIgnitionService(registror);
    }

    @Bean
    public FireRetrievalUseCase retrievalUseCase(@Autowired OngoingFiresInventory fires) {
        return new FiresQueryService(fires);
    }
}
