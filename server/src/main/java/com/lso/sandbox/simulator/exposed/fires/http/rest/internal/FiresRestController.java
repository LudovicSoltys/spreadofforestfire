package com.lso.sandbox.simulator.exposed.fires.http.rest.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.*;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FirePropagationUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Définition de l'API HTTP REST de gestion des incendies
 */
@RestController
public class FiresRestController implements FiresEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(FiresRestController.class);

    private final FireIgnitionUseCase ignitionUseCase;

    private final FirePropagationUseCase propagationUseCase;

    private final FireRetrievalUseCase retrieveUseCase;

    public FiresRestController(FireIgnitionUseCase ignitionUseCase, FirePropagationUseCase propagationUseCase, FireRetrievalUseCase retrieveUseCase) {
        this.ignitionUseCase = ignitionUseCase;
        this.propagationUseCase = propagationUseCase;
        this.retrieveUseCase = retrieveUseCase;
    }

    /**
     *
     * @param request le corps de la requête est inutile pour le moment
     * @return le compte-rendu de l'opération
     */
    @Override
    public FiresChangeResponse propagateFire(@RequestBody @Valid FiresPropagationRequest request) {

        LOG.debug("Message=Trying to spread fire;Request={};", request);

        FirePropagationUseCase.Input input = new PostNextFiresInput();
        PostNextFiresOutput output = new PostNextFiresOutput();

        propagationUseCase.execute(input, output);

        LOG.debug("Message=Operation of spreading fires is done;Result={};", output);

        return output;
    }

    /**
     *
     * @param request le corps de la requête contient la liste des incendies à ajouter
     * @return le compte-rendu de l'opération
     */
    @Override
    public FiresChangeResponse addFires(@RequestBody @Valid FiresAddRequest request) {

        LOG.debug("Message=Trying to add new fires on the field;Request={};", request);

        FireIgnitionUseCase.Input input = new PostFiresInput(request);
        PostFiresOutput output = new PostFiresOutput();

        ignitionUseCase.execute(input, output);

        LOG.debug("Message=Operation of adding fires is done;Result={};", output);

        return output;
    }

    /**
     *
     * @return la liste des incendies en cours
     */
    @Override
    public FireListResponse listFires() {

        LOG.debug("Message=Trying to list current fires on the field;");

        FireRetrievalUseCase.Input input = new FiresListInput();
        FiresListOutput output = new FiresListOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieving current fires is done;Result={};", output);

        return output;
    }
}
