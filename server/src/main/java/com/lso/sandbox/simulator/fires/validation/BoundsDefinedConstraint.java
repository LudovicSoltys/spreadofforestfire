package com.lso.sandbox.simulator.fires.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation de la contrainte d'existence d'un périmètre valide pour la simulation.
 */
@Constraint(validatedBy = BoundsDefinedConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface BoundsDefinedConstraint {

    String message() default "Board is not valid. No fire is possible.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
