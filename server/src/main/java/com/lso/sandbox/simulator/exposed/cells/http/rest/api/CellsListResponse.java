package com.lso.sandbox.simulator.exposed.cells.http.rest.api;

import com.lso.sandbox.simulator.shared.Response;

import java.util.List;

public interface CellsListResponse extends Response {

    List<CellsListResponseItem> getItems();

    int getTotalElements();
}
