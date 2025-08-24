package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.shared.Response;

interface FireListResponse extends Response {

    Iterable<FireListResponseItem> getContent();

    long getTotalElements();
}
