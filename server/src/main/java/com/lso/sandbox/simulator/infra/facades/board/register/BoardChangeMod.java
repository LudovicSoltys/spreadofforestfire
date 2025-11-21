package com.lso.sandbox.simulator.infra.facades.board.register;

import com.lso.sandbox.simulator.infra.facades.board.register.internal.BoardResetFacade;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataMod.class)
public class BoardChangeMod {

    @Bean
    public BoardResetFacade resetFacade(@Autowired BoardJpaCrudRepository boards, @Autowired CellJpaCrudRepository cells) {
        return new BoardResetFacade(boards, cells);
    }
}
