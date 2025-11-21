package com.lso.sandbox.simulator.infra.facades.fire.query;

import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Module de consultation des incendies
 */
@Configuration
@Import(DataMod.class)
public class FiresFacadeMod {

    @Bean
    public OngoingFiresInventory ongoingFiresInventory(@Autowired BoardJpaCrudRepository boards,
                                                       @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsReadingFacade(boards, inventory);
    }
}
