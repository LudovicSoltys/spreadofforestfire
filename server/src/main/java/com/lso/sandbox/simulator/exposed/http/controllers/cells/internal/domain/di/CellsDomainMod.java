package com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.di;

import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.api.CellsListUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.internal.CellsListService;
import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.spi.AllCellsSupplier;
import com.lso.sandbox.simulator.infra.facade.di.CellsFacadeMod;
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
