package com.lso.sandbox.simulator.shared.util;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Boîte à outils pour manipuler les {@link Iterable}s
 */
public class IterableUtils {

    /**
     * Ne doit pas être instancié
     */
    private IterableUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     *
     * @param values doit être non null
     * @return true si les données d'entrée sont vides. false, sinon.
     */
    public static boolean isEmpty(Iterable<?> values) {
        Objects.requireNonNull(values);
        return StreamSupport.stream(values.spliterator(), false).findAny().isEmpty();
    }

    /**
     *
     * @param values doit être non null
     * @return le nombre d'éléments fournis en entrées
     */
    public static long count(Iterable<?> values) {
        Objects.requireNonNull(values);
        return StreamSupport.stream(values.spliterator(), false).count();
    }

    /**
     *
     * @param values doit être non null
     * @param <T> le type des données
     * @return un nouveau {@link Stream} associé aux données d'entrée
     */
    public static <T> Stream<T> streamOf(Iterable<T> values) {
        Objects.requireNonNull(values);
        return StreamSupport.stream(values.spliterator(), false);
    }

    /**
     *
     * @param elements des données
     * @param others d'autres données
     * @param <T> le types des données d'entrée
     * @return un nouveau {@link Stream} associé aux données d'entrée
     */
    public static <T> Iterable<T> concat(Iterable<T> elements, Iterable<T> others) {
        return Stream.concat(streamOf(elements), streamOf(others)).toList();
    }
}
