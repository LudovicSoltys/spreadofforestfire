package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresAddRequest;
import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresChangeRequest;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.infra.repositories.DataMod;
import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Module dédié au domaine métier de la gestion des incendies : consultation, création et propagation.
 */
@Configuration
@Import(DataMod.class)
public class FireValidationMod {

    @Bean
    public ConstraintValidator<InBoundConstraint, FiresAddRequest.TargetItem> firesAddRequestInBoundConstraint(
            @Autowired BoardJpaCrudRepository boards
    ) {
        return new InBoundConstraintValidator(boards);
    }

    @Bean
    public ConstraintValidator<BoundsDefinedConstraint, FiresChangeRequest> firesChangeRequestBoundsDefinedConstraintValidator(
            @Autowired BoardJpaCrudRepository boards
    ) {
        return new BoundsDefinedConstraintValidator(boards);
    }

    @Bean
    public ConstraintValidator<StillAliveConstraint, FiresAddRequest.TargetItem> firesAddRequestStillAliveConstraintValidator(
            @Autowired CellJpaQueryRepository cells
    ) {
        return new StillAliveConstraintValidator(cells);
    }
}
