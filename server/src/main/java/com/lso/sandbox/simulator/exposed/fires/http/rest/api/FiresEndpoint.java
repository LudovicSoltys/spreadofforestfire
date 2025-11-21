package com.lso.sandbox.simulator.exposed.fires.http.rest.api;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Définition de l'API HTTP REST de gestion des incendies
 */
@Validated
@RequestMapping("/api/fires")
public interface FiresEndpoint {

    /**
     *
     * @param request le corps de la requête est inutile pour le moment
     * @return le compte-rendu de l'opération
     */
    @PostMapping(
            value = "/next",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    FiresChangeResponse propagateFire(@RequestBody @Valid FiresPropagationRequest request);

    /**
     *
     * @param request le corps de la requête contient la liste des incendies à ajouter
     * @return le compte-rendu de l'opération
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    FiresChangeResponse addFires(@RequestBody @Valid FiresAddRequest request);

    /**
     *
     * @return la liste des incendies en cours
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    FireListResponse listFires();
}
