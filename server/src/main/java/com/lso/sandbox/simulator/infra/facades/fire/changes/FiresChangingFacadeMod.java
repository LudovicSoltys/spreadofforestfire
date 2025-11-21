package com.lso.sandbox.simulator.infra.facades.fire.changes;

import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.changes.internal.BurningCellsAdditionFacade;
import com.lso.sandbox.simulator.infra.facades.fire.changes.internal.BurningCellsRemovalFacade;
import com.lso.sandbox.simulator.infra.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Module de création de nouveaux incendies
 */
@Configuration
@Import(DataMod.class)
public class FiresChangingFacadeMod {

    @Bean("firesAdditionRegistror")
    public FiresRegistrar firesAdditionRegistror(@Autowired CellJpaCrudRepository repository,
                                                 @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsAdditionFacade(repository, inventory);
    }

    @Bean("firesRemovalRegistror")
    public FiresRegistrar firesRemovalRegistror(@Autowired CellJpaCrudRepository repository,
                                                @Autowired CellJpaQueryRepository inventory) {
        return new BurningCellsRemovalFacade(repository, inventory);
    }
}
