package com.lso.sandbox.simulator.infra.facade.internal;

import com.lso.sandbox.simulator.exposed.init.components.internal.domain.spi.BoardInitProcessor;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import jakarta.transaction.Transactional;

public class BoardInitFacade implements BoardInitProcessor {

    private final BoardJpaCrudRepository boards;

    private final CellJpaCrudRepository cells;

    public BoardInitFacade(BoardJpaCrudRepository boards, CellJpaCrudRepository cells) {
        this.boards = boards;
        this.cells = cells;
    }

    @Override
    @Transactional
    public void accept(Void unused) {

        this.cells.deleteAll();

        this.boards.deleteAll();
    }
}
