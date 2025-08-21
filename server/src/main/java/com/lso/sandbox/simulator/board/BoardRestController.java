package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.board.registror.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.board.supplier.BoardRetrievalUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    private static final Logger LOG = LoggerFactory.getLogger(BoardRestController.class);

    private final BoardRetrievalUseCase retrieveUseCase;

    private final BoardUpdatingUseCase updateUseCase;

    public BoardRestController(BoardRetrievalUseCase retrieveUseCase, BoardUpdatingUseCase updateUseCase) {
        this.retrieveUseCase = retrieveUseCase;
        this.updateUseCase = updateUseCase;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PostBoardResponse changeBoard(@RequestBody @Valid PostBoardRequest request) {

        LOG.debug("Message=Trying to change board;Parameters={};", request);

        BoardUpdatingUseCase.Input input = new PostBoardInput(request);
        PostBoardOutput output = new PostBoardOutput();

        updateUseCase.execute(input, output);

        LOG.debug("Message=Operation of change is done;Result={};", output);

        return output;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BoardGetResponse getBoard() {

        LOG.debug("Message=Trying to retrieve current board;");

        BoardRetrievalUseCase.Input input = new GetBoardInput();
        BoardGetOutput output = new BoardGetOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieval of the board is done;Result={};", output);

        return output;
    }

}
