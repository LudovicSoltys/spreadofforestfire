package com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain;

import com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.api.CellsListUseCase;
import com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.internal.CellsListService;
import com.lso.sandbox.simulator.infra.facades.cells.list.CellsFacadeMod;
import com.lso.sandbox.simulator.infra.facades.cells.list.api.AllCellsSupplier;
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
