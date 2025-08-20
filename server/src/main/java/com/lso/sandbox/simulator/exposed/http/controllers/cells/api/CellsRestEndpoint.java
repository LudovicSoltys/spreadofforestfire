package com.lso.sandbox.simulator.exposed.http.controllers.cells.api;

import com.lso.sandbox.simulator.exposed.http.shared.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/cells")
public interface CellsRestEndpoint {

    @GetMapping
    CellsListResponse all();

    interface CellsListResponse extends Response {

        List<CellsListResponseItem> getItems();

        int getTotalElements();
    }

    interface CellsListResponseItem {
        int getX();

        int getY();

        boolean isBurning();

        boolean isDead();
    }
}
