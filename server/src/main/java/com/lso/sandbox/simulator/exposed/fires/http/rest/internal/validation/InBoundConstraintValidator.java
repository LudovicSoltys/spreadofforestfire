package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresAddRequest;
import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresAddRequest.TargetItem;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.Bounds;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.BoardJpaEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implémentation de {@link ConstraintValidator}. Permet de valider qu'un {@link FiresAddRequest.TargetItem} est dans le
 * périmètre de la simulation.
 */
public class InBoundConstraintValidator implements ConstraintValidator<InBoundConstraint, TargetItem> {

    private final BoardJpaCrudRepository boards;

    public InBoundConstraintValidator(BoardJpaCrudRepository boards) {
        this.boards = boards;
    }

    @Override
    public boolean isValid(TargetItem value, ConstraintValidatorContext context) {

        if (boards.isEmpty()) {
            return false;
        }

        Bounds bounds = boards.first().map(TargetItemBounds::new);
        return bounds.contains(value);
    }

    static class TargetItemBounds implements Bounds {

        private final BoardJpaEntity entity;

        public TargetItemBounds(BoardJpaEntity entity) {
            this.entity = entity;
        }

        @Override
        public byte yMin() {
            return 0;
        }

        @Override
        public byte yMax() {
            return entity.getHeight();
        }

        @Override
        public byte xMin() {
            return 0;
        }

        @Override
        public byte xMax() {
            return entity.getWidth();
        }
    }
}