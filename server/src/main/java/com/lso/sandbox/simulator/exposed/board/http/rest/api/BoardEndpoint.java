package com.lso.sandbox.simulator.exposed.board.http.rest.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board")
public interface BoardEndpoint {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    PostBoardResponse changeBoard(@RequestBody @Valid PostBoardRequest request);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    BoardGetResponse getBoard();

}
