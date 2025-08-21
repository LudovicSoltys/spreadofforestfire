package com.lso.sandbox.simulator.shared;

import java.util.List;

public interface Response {

    List<Message> getMessages();

    List<Message> getErrors();
}
