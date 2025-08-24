package com.lso.sandbox.simulator.repositories.facades.fire.changes;

import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.data.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Module de création de nouveaux incendies
 */
@Configuration
@Import(DataMod.class)
public class FiresChangingFacadeMod {

    @Bean("firesUpdatingRegistror")
    public FiresRegistror firesUpdatingFacade(@Autowired BoardJpaCrudRepository boardJpaCrudRepository,
                                              @Autowired @Qualifier("firesAdditionRegistror") FiresRegistror firesAdditionRegistror,
                                              @Autowired @Qualifier("firesRemovalRegistror") FiresRegistror firesRemovalRegistror) {
        return new BurningCellsUpdatingFacade(boardJpaCrudRepository, firesAdditionRegistror, firesRemovalRegistror);
    }

    @Bean("firesAdditionRegistror")
    public FiresRegistror firesAdditionRegistror(@Autowired CellJpaCrudRepository repository,
                                                 @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsAdditionFacade(repository, inventory);
    }

    @Bean("firesRemovalRegistror")
    public FiresRegistror firesRemovalRegistror(@Autowired CellJpaCrudRepository repository,
                                                @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsRemovalFacade(repository, inventory);
    }
}
