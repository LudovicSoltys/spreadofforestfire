package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.shared.Response;

interface FiresChangeResponse extends Response {

    Iterable<PostFiresOutputChanges> getChanges();
}
