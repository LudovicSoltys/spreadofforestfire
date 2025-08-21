package com.lso.sandbox.simulator.fires.add.facade;

import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class FiresAddFacadeMod {

    @Bean
    public FiresRegistror firesRegistror(@Autowired BoardJpaCrudRepository boards,
                                         @Autowired CellJpaCrudRepository repository,
                                         @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsUpdatingFacade(boards, repository, inventory);
    }
}
