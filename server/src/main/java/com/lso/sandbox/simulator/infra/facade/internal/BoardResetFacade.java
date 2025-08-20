package com.lso.sandbox.simulator.infra.facade.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.BoardRegistror;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaEntity;
import com.lso.sandbox.simulator.infra.data.api.CellJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.CellJpaEntity;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardResetFacade implements BoardRegistror {

    private final BoardJpaCrudRepository boards;

    private final CellJpaCrudRepository cells;

    public BoardResetFacade(BoardJpaCrudRepository boards, CellJpaCrudRepository cells) {
        this.boards = boards;
        this.cells = cells;
    }

    @Override
    public Either<Errors, Void> deleteAll() {
        this.cells.deleteAll();
        this.boards.deleteAll();

        return Either.right(null);
    }

    @Override
    public Either<Errors, CreatedBoard> save(BoardToCreate attempt) {

        byte rows = attempt.getWidth(); // Specify the number of rows
        byte columns = attempt.getHeight(); // Specify the number of columns

        BoardJpaEntity created = this.boards.save(new BoardJpaEntity(rows, columns));

        Iterable<CellJpaEntity> entities = IntStream.range(0, rows)
                .mapToObj(i -> IntStream.range(0, columns).mapToObj(operand -> new CellJpaEntity((byte) i, (byte) operand)))
                .flatMap(Function.identity())
                .collect(Collectors.toUnmodifiableSet());

        this.cells.saveAll(entities);

        return Either.right(created.map(entity -> null));
    }
}
