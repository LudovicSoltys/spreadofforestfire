package com.lso.sandbox.simulator.exposed.cells.http.rest.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/cells")
public interface CellsEndpoint {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    CellsListResponse all();
}
