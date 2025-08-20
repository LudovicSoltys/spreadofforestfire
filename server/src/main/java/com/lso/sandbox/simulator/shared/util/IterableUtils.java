package com.lso.sandbox.simulator.shared.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableUtils {

    private IterableUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isEmpty(Iterable<?> values) {
        return StreamSupport.stream(values.spliterator(), false).findAny().isEmpty();
    }

    public static long count(Iterable<?> values) {
        return StreamSupport.stream(values.spliterator(), false).count();
    }

    public static <T> Stream<T> streamOf(Iterable<T> values) {
        return StreamSupport.stream(values.spliterator(), false);
    }

    public static <T> Iterable<T> concat(Iterable<T> elements, Iterable<T> others) {
        return Stream.concat(streamOf(elements), streamOf(others)).toList();
    }
}
