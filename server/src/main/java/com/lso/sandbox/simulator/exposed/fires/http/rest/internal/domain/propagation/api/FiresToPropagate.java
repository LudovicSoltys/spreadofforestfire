package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFire;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.infra.facades.board.read.api.OneSingleBoard;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import com.lso.sandbox.simulator.shared.util.Mappable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Paquet de {@link OngoingFire}s
 */
public interface FiresToPropagate extends Iterable<FireToPropagate>, Mappable<FiresToPropagate> {

    /**
     *
     * @return le numéro de l'étape concernée
     */
    int step();

    /**
     *
     * @return true s'il n'y a aucun feu en cours
     */
    boolean isEmpty();

    /**
     *
     * @return les possibles localisations des feux suivants
     */
    Iterable<NextFire> around();

    /**
     *
     * @return un nouveau {@link Stream}
     */
    default Stream<FireToPropagate> stream() {
        return IterableUtils.streamOf(this);
    }

    /**
     *
     * @param board doit être non null
     * @param values doit être non null
     * @return une instance immuable de {@link FiresToPropagate}
     */
    static FiresToPropagate of(OneSingleBoard board, OngoingFires values) {
        return new ImmutableFiresToPropagate(board, values);
    }

    /**
     * Implémentation basique de {@link FiresToPropagate}
     */
    class ImmutableFiresToPropagate implements FiresToPropagate {

        private final int index;

        private final Bounds context;

        private final List<FireToPropagate> values;

        /**
         * Crée une nouvelle instance
         * @param board doit être non null
         * @param data doit être non null
         */
        ImmutableFiresToPropagate(OneSingleBoard board, OngoingFires data) {
            Objects.requireNonNull(board);
            Objects.requireNonNull(data);

            context = board.map(Bounds::of);
            this.index = board.currentStep();
            this.values = data.stream()
                    .map(element -> FireToPropagate.of(this.index, element))
                    .toList();
        }

        @Override
        public boolean isEmpty() {
            return values.isEmpty();
        }

        @Override
        public int step() {
            return index;
        }

        @Override
        public Iterator<FireToPropagate> iterator() {
            return values.iterator();
        }

        @Override
        public Iterable<NextFire> around() {

            return this.values.stream()
                    .flatMap(value -> IterableUtils.streamOf(value.around()))
                    // évite les doublons
                    .distinct()
                    // ne garde que les positions qui sont sur le terrain
                    .filter(context::contains)
                    // exclut les coordonnées qui seraient déjà dans le paquet de données source
                    .filter(item -> IterableUtils.streamOf(values)
                            .noneMatch(element -> element.getX() == item.getX() && element.getY() == item.getY()))
                    .toList();
        }
    }
}
