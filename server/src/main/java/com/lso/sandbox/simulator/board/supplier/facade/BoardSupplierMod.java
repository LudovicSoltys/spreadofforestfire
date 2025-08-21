package com.lso.sandbox.simulator.board.supplier.facade;

import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.DataMod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class BoardSupplierMod {

    @Bean
    public CurrentBoardSupplier boardSupplier(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        return new BoardReadingFacade(boards, stats);
    }
}
