package com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.di;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.internal.BoardReplaceService;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.internal.BoardRetrieveService;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.BoardRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facade.di.BoardFacadeMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( BoardFacadeMod.class)
public class BoardDomainMod {

    @Bean
    public BoardRetrievalUseCase retrieveUseCase(@Autowired CurrentBoardSupplier supplier) {
        return new BoardRetrieveService(supplier);
    }

    @Bean
    public BoardUpdatingUseCase updateUseCase(@Autowired BoardRegistror registror) {
        return new BoardReplaceService(registror);
    }
}
