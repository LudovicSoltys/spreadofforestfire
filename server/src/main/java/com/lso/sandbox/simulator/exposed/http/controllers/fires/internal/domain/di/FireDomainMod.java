package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.di;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal.FiresIgnitionService;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal.FiresPropagationService;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal.FiresQueryService;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FireHandlingProcessor;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.OngoingFiresInventory;
import com.lso.sandbox.simulator.infra.engine.di.FireSpreadingEngineMod;
import com.lso.sandbox.simulator.infra.facade.di.BoardFacadeMod;
import com.lso.sandbox.simulator.infra.facade.di.FiresFacadeMod;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( {BoardFacadeMod.class, FireSpreadingEngineMod.class, FiresFacadeMod.class})
public class FireDomainMod {

    @Bean
    public FirePropagationUseCase propagateUseCase(@Autowired CurrentBoardSupplier supplier,
                                                 @Autowired OngoingFiresInventory inventory,
                                                 @Autowired FireHandlingProcessor engine,
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
}
