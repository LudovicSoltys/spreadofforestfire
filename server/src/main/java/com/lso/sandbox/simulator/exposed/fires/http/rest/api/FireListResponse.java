package com.lso.sandbox.simulator.exposed.fires.http.rest.api;

import com.lso.sandbox.simulator.shared.Response;

public interface FireListResponse extends Response {

    Iterable<FireListResponseItem> getContent();

    long getTotalElements();
}
