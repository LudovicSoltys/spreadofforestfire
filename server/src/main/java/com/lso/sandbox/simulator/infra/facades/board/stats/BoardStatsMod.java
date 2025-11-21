package com.lso.sandbox.simulator.infra.facades.board.stats;

import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class BoardStatsMod {

    @Bean
    public BoardStatsRequestor boardStats(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        return new BoardStatsFacade(boards, stats);
    }
}
