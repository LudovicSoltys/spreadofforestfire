package com.lso.sandbox.simulator.utils;

public class RandomUtils {

    private RandomUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int randomInt(int startInclusive, int endExclusive) {
        return org.apache.commons.lang3.RandomUtils.secure().randomInt(startInclusive, endExclusive);
    }
}
