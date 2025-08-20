package com.lso.sandbox.simulator.infra.facade.di;

import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.spi.AllCellsSupplier;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.di.DataMod;
import com.lso.sandbox.simulator.infra.facade.internal.AllCellsReadingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class CellsFacadeMod {

    @Bean
    public AllCellsSupplier allCellsSupplier(@Autowired CellJpaCrudRepository repository) {
        return new AllCellsReadingFacade(repository);
    }
}
