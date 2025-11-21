package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.api.FiresAddRequest;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation de la contrainte de validation qu'un {@link FiresAddRequest.TargetItem} est effectivement à l'intérieur du périmètre de la
 * simulation.
 */
@Constraint(validatedBy = InBoundConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface InBoundConstraint {
    String message() default "The input target is out of bounds.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
