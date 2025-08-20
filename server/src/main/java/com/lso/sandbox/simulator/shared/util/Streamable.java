package com.lso.sandbox.simulator.shared.util;

import java.util.stream.Stream;

public interface Streamable<T> extends Iterable<T> {

    default Stream<T> stream() {
        return IterableUtils.streamOf(this);
    }
}
