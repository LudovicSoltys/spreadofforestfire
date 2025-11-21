package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.internal.BoardReplaceService;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.internal.BoardRetrieveService;
import com.lso.sandbox.simulator.infra.facades.board.read.BoardSupplierMod;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.register.api.BoardRegistrar;
import com.lso.sandbox.simulator.infra.facades.board.stats.BoardStatsMod;
import com.lso.sandbox.simulator.infra.facades.board.stats.BoardStatsRequestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( {BoardSupplierMod.class, BoardStatsMod.class})
public class BoardDomainMod {

    @Bean
    public BoardRetrievalUseCase retrieveUseCase(@Autowired CurrentBoardSupplier supplier,
                                                 @Autowired BoardStatsRequestor stats) {
        return new BoardRetrieveService(supplier, stats);
    }

    @Bean
    public BoardUpdatingUseCase updateUseCase(@Autowired BoardRegistrar registror) {
        return new BoardReplaceService(registror);
    }
}
