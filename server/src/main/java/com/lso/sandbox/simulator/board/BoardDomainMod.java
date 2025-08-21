package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.board.registror.BoardReplaceService;
import com.lso.sandbox.simulator.board.registror.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.board.registror.facade.BoardRegistror;
import com.lso.sandbox.simulator.board.supplier.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.board.supplier.BoardRetrieveService;
import com.lso.sandbox.simulator.board.supplier.facade.BoardSupplierMod;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import( BoardSupplierMod.class)
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
