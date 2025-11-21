package com.lso.sandbox.simulator.exposed.board.http.rest.internal;

import com.lso.sandbox.simulator.exposed.board.http.rest.api.BoardEndpoint;
import com.lso.sandbox.simulator.exposed.board.http.rest.api.BoardGetResponse;
import com.lso.sandbox.simulator.exposed.board.http.rest.api.PostBoardRequest;
import com.lso.sandbox.simulator.exposed.board.http.rest.api.PostBoardResponse;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardRestController implements BoardEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(BoardRestController.class);

    private final BoardRetrievalUseCase retrieveUseCase;

    private final BoardUpdatingUseCase updateUseCase;

    public BoardRestController(BoardRetrievalUseCase retrieveUseCase, BoardUpdatingUseCase updateUseCase) {
        this.retrieveUseCase = retrieveUseCase;
        this.updateUseCase = updateUseCase;
    }

    @Override
    public PostBoardResponse changeBoard(@RequestBody @Valid PostBoardRequest request) {

        LOG.debug("Message=Trying to change board;Parameters={};", request);

        BoardUpdatingUseCase.Input input = new PostBoardInput(request);
        PostBoardOutput output = new PostBoardOutput();

        updateUseCase.execute(input, output);

        LOG.debug("Message=Operation of change is done;Result={};", output);

        return output;
    }

    @Override
    public BoardGetResponse getBoard() {

        LOG.debug("Message=Trying to retrieve current board;");

        BoardRetrievalUseCase.Input input = new GetBoardInput();
        BoardGetOutput output = new BoardGetOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieval of the board is done;Result={};", output);

        return output;
    }
}
