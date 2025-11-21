package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresAddRequest;
import com.lso.sandbox.simulator.infra.repositories.CellJpaEntity;
import com.lso.sandbox.simulator.infra.repositories.CellJpaQueryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implémentation de {@link ConstraintValidator}. Permet de valider qu'un {@link FiresAddRequest.TargetItem} est valide
 * pour être incendié (c'est-à-dire qu'il n'a pas déjà été incendié.
 */
public class StillAliveConstraintValidator implements ConstraintValidator<StillAliveConstraint, FiresAddRequest.TargetItem> {

    private final CellJpaQueryRepository cells;

    public StillAliveConstraintValidator(CellJpaQueryRepository cells) {
        this.cells = cells;
    }

    @Override
    public boolean isValid(FiresAddRequest.TargetItem value, ConstraintValidatorContext context) {

        return cells.findByXAndY(value.getX(), value.getY()).filter(CellJpaEntity::isAlive).isPresent();
    }
}