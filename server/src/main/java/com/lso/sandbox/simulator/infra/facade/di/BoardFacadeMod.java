package com.lso.sandbox.simulator.infra.facade.di;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.CurrentBoardSupplier;
import com.lso.sandbox.simulator.exposed.init.components.internal.domain.spi.BoardInitProcessor;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.infra.data.di.DataMod;
import com.lso.sandbox.simulator.infra.facade.internal.BoardInitFacade;
import com.lso.sandbox.simulator.infra.facade.internal.BoardReadingFacade;
import com.lso.sandbox.simulator.infra.facade.internal.BoardResetFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class BoardFacadeMod {

    @Bean
    public BoardInitProcessor initProcessor(@Autowired BoardJpaCrudRepository boards, @Autowired CellJpaCrudRepository cells) {
        return new BoardInitFacade(boards, cells);
    }

    @Bean
    public CurrentBoardSupplier boardSupplier(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        return new BoardReadingFacade(boards, stats);
    }

    @Bean
    public BoardResetFacade resetFacade(@Autowired BoardJpaCrudRepository boards, @Autowired CellJpaCrudRepository cells) {
        return new BoardResetFacade(boards, cells);
    }
}
