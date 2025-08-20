package com.lso.sandbox.simulator.shared.validation;

import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface Errors {
    default void reject(String errorCode) {
        reject(errorCode, null, null);
    }

    default void reject(String errorCode, String defaultMessage) {
        reject(errorCode, null, defaultMessage);
    }

    void reject(String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage);

    default boolean hasErrors() {
        return !getAllErrors().isEmpty();
    }

    default int getErrorCount() {
        return getAllErrors().size();
    }

    List<ObjectError> getAllErrors();
}
