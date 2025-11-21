package com.lso.sandbox.simulator.infra.facades.board.read;

import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.read.internal.BoardReadingFacade;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class BoardSupplierMod {

    @Bean
    public CurrentBoardSupplier boardSupplier(@Autowired BoardJpaCrudRepository boards) {
        return new BoardReadingFacade(boards);
    }
}
