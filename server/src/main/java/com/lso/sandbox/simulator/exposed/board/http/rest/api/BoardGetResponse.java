package com.lso.sandbox.simulator.exposed.board.http.rest.api;

import com.lso.sandbox.simulator.shared.Response;

public interface BoardGetResponse extends Response {

    boolean isPresent();

    long getWidth();

    long getHeight();

    int getCurrentStep();
}
