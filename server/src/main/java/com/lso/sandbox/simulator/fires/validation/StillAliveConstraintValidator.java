package com.lso.sandbox.simulator.fires.validation;

import com.lso.sandbox.simulator.fires.FiresAddRequest;
import com.lso.sandbox.simulator.repositories.CellJpaEntity;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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