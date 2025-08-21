package com.lso.sandbox.simulator.fires.list.facade;

import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class FiresFacadeMod {

    @Bean
    public OngoingFiresInventory ongoingFiresInventory(@Autowired BoardJpaCrudRepository boards,
                                                       @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsReadingFacade(boards, inventory);
    }
}
