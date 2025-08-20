package com.lso.sandbox.simulator.shared.util;

import java.util.Objects;
import java.util.function.Function;

public interface Mappable<R> {

    default <U> U map(Function<? super R, ? extends U> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return mapper.apply((R) this);
    }
}
