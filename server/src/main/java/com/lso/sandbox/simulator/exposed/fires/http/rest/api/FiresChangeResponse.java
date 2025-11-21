package com.lso.sandbox.simulator.exposed.fires.http.rest.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.PostFiresOutputChanges;
import com.lso.sandbox.simulator.shared.Response;

public interface FiresChangeResponse extends Response {

    Iterable<PostFiresOutputChanges> getChanges();
}
