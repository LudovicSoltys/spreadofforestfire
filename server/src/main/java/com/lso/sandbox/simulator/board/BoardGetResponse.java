package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.shared.Response;

interface BoardGetResponse extends Response {

    boolean isPresent();

    long getWidth();

    long getHeight();

    int getCurrentStep();
}
