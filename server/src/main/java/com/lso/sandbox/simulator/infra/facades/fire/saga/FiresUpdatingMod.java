package com.lso.sandbox.simulator.infra.facades.fire.saga;

import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.changes.internal.BurningCellsUpdatingFacade;
import com.lso.sandbox.simulator.infra.facades.fire.saga.api.FiresSagaRegistrar;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
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
public class FiresUpdatingMod {

    @Bean("firesUpdatingRegistror")
    public FiresSagaRegistrar firesUpdatingFacade(@Autowired BoardJpaCrudRepository boardJpaCrudRepository,
                                                  @Autowired @Qualifier("firesAdditionRegistror") FiresRegistrar firesAdditionRegistror,
                                                  @Autowired @Qualifier("firesRemovalRegistror") FiresRegistrar firesRemovalRegistror) {
        return new BurningCellsUpdatingFacade(boardJpaCrudRepository, firesAdditionRegistror, firesRemovalRegistror);
    }
}
