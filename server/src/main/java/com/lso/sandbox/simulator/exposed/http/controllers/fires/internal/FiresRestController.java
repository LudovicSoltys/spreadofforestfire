package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.api.FiresRestEndpoint;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.shared.Message;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FiresRestController implements FiresRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(FiresRestController.class);

    private final FireIgnitionUseCase ignitionUseCase;

    private final FirePropagationUseCase propagationUseCase;

    private final FireRetrievalUseCase retrieveUseCase;

    public FiresRestController(FireIgnitionUseCase ignitionUseCase, FirePropagationUseCase propagationUseCase, FireRetrievalUseCase retrieveUseCase) {
        this.ignitionUseCase = ignitionUseCase;
        this.propagationUseCase = propagationUseCase;
        this.retrieveUseCase = retrieveUseCase;
    }

    @Override
    public FiresChangeResponse propagateFire(FiresPropagationRequest request) {

        LOG.debug("Message=Trying to spread fire;Request={};", request);

        FirePropagationUseCase.Input input = new PostNextFiresInput();
        PostNextFiresOutput output = new PostNextFiresOutput();

        propagationUseCase.execute(input, output);

        LOG.debug("Message=Operation of spreading fires is done;Result={};", output);

        return output;
    }

    @Override
    public FiresChangeResponse addFires(FiresAddRequest request) {

        LOG.debug("Message=Trying to add new fires on the field;Request={};", request);

        FireIgnitionUseCase.Input input = new PostFiresInput(request);
        PostFiresOutput output = new PostFiresOutput();

        ignitionUseCase.execute(input, output);

        LOG.debug("Message=Operation of adding fires is done;Result={};", output);

        return output;
    }

    @Override
    public FireListResponse listFires() {

        LOG.debug("Message=Trying to list current fires on the field;");

        FireRetrievalUseCase.Input input = new FiresListInput();
        FiresListOutput output = new FiresListOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieving current fires is done;Result={}", output);

        return output;
    }

    static class FiresListInput implements FireRetrievalUseCase.Input {

    }

    static class FiresListOutput implements FiresRestEndpoint.FireListResponse, FireRetrievalUseCase.Output {

        private Iterable<Coordinates> content = List.of();

        private final List<Message> messages = new ArrayList<>();

        private final List<Message> errors = new ArrayList<>();

        @Override
        public Iterable<Coordinates> getContent() {
            return content;
        }

        @Override
        public long getTotalElements() {
            return IterableUtils.count(content);
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public List<Message> getErrors() {
            return errors;
        }

        @Override
        public void accept(Iterable<Coordinates> values) {

            this.content = values;

            if (IterableUtils.isEmpty(values)) {
                messages.add(Message.of("fires.empty"));
            }
        }

        @Override
        public void reject(Errors errors) {

            errors
                    .getAllErrors()
                    .forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
        }
    }

    static class PostFiresInput implements FireIgnitionUseCase.Input {

        private final FiresAddRequest params;

        public PostFiresInput(FiresAddRequest params) {
            this.params = params;
        }

        @Override
        public Iterable<Coordinates> targets() {
            return params.getTargets().stream()
                    .map(value -> Coordinates.of(value.x(), value.y()))
                    .toList();
        }
    }

    static class PostFiresOutput implements FiresRestEndpoint.FiresChangeResponse, FireIgnitionUseCase.Output {

        private Iterable<CellChanges> changes;

        private final List<Message> messages = new ArrayList<>();

        private final List<Message> errors = new ArrayList<>();

        @Override
        public Iterable<CellChanges> getChanges() {
            return changes;
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public List<Message> getErrors() {
            return errors;
        }

        @Override
        public void accept(Iterable<CellChanges> values) {

            this.changes = values;

            if (IterableUtils.isEmpty(values)) {
                messages.add(Message.of("fires.empty"));
            }
        }

        @Override
        public void reject(Errors errors) {

            errors.getAllErrors().forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
        }
    }


    static class PostNextFiresInput implements FirePropagationUseCase.Input {

    }

    static class PostNextFiresOutput implements FiresRestEndpoint.FiresChangeResponse, FirePropagationUseCase.Output {

        private Iterable<CellChanges> changes;

        private final List<Message> messages = new ArrayList<>();

        private final List<Message> errors = new ArrayList<>();

        @Override
        public Iterable<CellChanges> getChanges() {
            return changes;
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public List<Message> getErrors() {
            return errors;
        }

        @Override
        public void accept(Iterable<CellChanges> values) {
            this.changes = values;
        }

        @Override
        public void reject(Errors errors) {
            errors
                    .getAllErrors()
                    .forEach(item -> this.errors.add(Message.of(item.getCode(), item.getArguments(), item.getDefaultMessage())));
        }
    }
}
