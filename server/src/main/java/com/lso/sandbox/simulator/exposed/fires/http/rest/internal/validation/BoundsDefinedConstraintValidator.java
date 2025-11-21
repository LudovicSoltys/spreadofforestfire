package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresChangeRequest;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implémentation de {@link ConstraintValidator}. Permet de valider que le périmètre de la simulation est bien défini.
 */
public class BoundsDefinedConstraintValidator implements ConstraintValidator<BoundsDefinedConstraint, FiresChangeRequest> {

    private final BoardJpaCrudRepository boards;

    public BoundsDefinedConstraintValidator(BoardJpaCrudRepository boards) {
        this.boards = boards;
    }

    @Override
    public boolean isValid(FiresChangeRequest value, ConstraintValidatorContext context) {

        if (boards.isEmpty()) {
            return false;
        }

        BoardJpaEntity entity = boards.first();
        return entity.getHeight() > 0 && entity.getWidth() > 0 && entity.getLastStep() >= 0;
    }
}