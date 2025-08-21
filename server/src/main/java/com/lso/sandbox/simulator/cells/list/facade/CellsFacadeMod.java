package com.lso.sandbox.simulator.cells.list.facade;

import com.lso.sandbox.simulator.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.DataMod;
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
