package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/fires")
public class FiresRestController {

    private static final Logger LOG = LoggerFactory.getLogger(FiresRestController.class);

    private final FireIgnitionUseCase ignitionUseCase;

    private final FirePropagationUseCase propagationUseCase;

    private final FireRetrievalUseCase retrieveUseCase;

    public FiresRestController(FireIgnitionUseCase ignitionUseCase, FirePropagationUseCase propagationUseCase, FireRetrievalUseCase retrieveUseCase) {
        this.ignitionUseCase = ignitionUseCase;
        this.propagationUseCase = propagationUseCase;
        this.retrieveUseCase = retrieveUseCase;
    }

    @PostMapping(
            value = "/next",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FiresChangeResponse propagateFire(@RequestBody @Valid FiresPropagationRequest request) {

        LOG.debug("Message=Trying to spread fire;Request={};", request);

        FirePropagationUseCase.Input input = new PostNextFiresInput();
        PostNextFiresOutput output = new PostNextFiresOutput();

        propagationUseCase.execute(input, output);

        LOG.debug("Message=Operation of spreading fires is done;Result={};", output);

        return output;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FiresChangeResponse addFires(@RequestBody @Valid FiresAddRequest request) {

        LOG.debug("Message=Trying to add new fires on the field;Request={};", request);

        FireIgnitionUseCase.Input input = new PostFiresInput(request);
        PostFiresOutput output = new PostFiresOutput();

        ignitionUseCase.execute(input, output);

        LOG.debug("Message=Operation of adding fires is done;Result={};", output);

        return output;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FireListResponse listFires() {

        LOG.debug("Message=Trying to list current fires on the field;");

        FireRetrievalUseCase.Input input = new FiresListInput();
        FiresListOutput output = new FiresListOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieving current fires is done;Result={}", output);

        return output;
    }
}
