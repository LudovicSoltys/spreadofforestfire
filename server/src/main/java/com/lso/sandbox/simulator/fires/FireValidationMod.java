package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.validation.*;
import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.repositories.data.DataMod;
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
