package com.lso.sandbox.simulator.exposed.http.shared;

import java.util.List;

public interface Response {

    List<Message> getMessages();

    List<Message> getErrors();
}
