package com.lso.sandbox.simulator.cells;

import com.lso.sandbox.simulator.cells.list.facade.AllCellsSupplier;
import com.lso.sandbox.simulator.cells.list.facade.CellsFacadeMod;
import com.lso.sandbox.simulator.cells.list.CellsListService;
import com.lso.sandbox.simulator.cells.list.CellsListUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( CellsFacadeMod.class)
public class CellsDomainMod {

    @Bean
    public CellsListUseCase listUseCase(@Autowired AllCellsSupplier supplier) {
        return new CellsListService(supplier);
    }
}
