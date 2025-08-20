package com.lso.sandbox.simulator.shared.validation;

import org.springframework.validation.ObjectError;

import java.util.List;

public class SimpleErrors implements Errors {

    @Override
    public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

    }

    @Override
    public List<ObjectError> getAllErrors() {
        return List.of();
    }
}
