package com.lso.sandbox.simulator.infra.facade.di;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.OngoingFiresInventory;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaQueryRepository;
import com.lso.sandbox.simulator.infra.data.di.DataMod;
import com.lso.sandbox.simulator.infra.facade.internal.BurningCellsReadingFacade;
import com.lso.sandbox.simulator.infra.facade.internal.BurningCellsUpdatingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class FiresFacadeMod {

    @Bean
    public FiresRegistror firesRegistror(@Autowired BoardJpaCrudRepository boards,
                                         @Autowired CellJpaCrudRepository repository,
                                         @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsUpdatingFacade(boards, repository, inventory);
    }

    @Bean
    public OngoingFiresInventory ongoingFiresInventory(@Autowired BoardJpaCrudRepository boards,
                                                       @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsReadingFacade(boards, inventory);
    }
}
