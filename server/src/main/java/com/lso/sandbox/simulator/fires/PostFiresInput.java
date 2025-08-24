package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.FireChangeToApply;
import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;

import java.util.Iterator;
import java.util.List;

/**
 * Données d'entrée du besoin métier de permettre la création de nouveaux incendies
 */
class PostFiresInput implements FireIgnitionUseCase.Input {

    private final FiresAddRequest params;

    public PostFiresInput(FiresAddRequest params) {
        this.params = params;
    }

    @Override
    public FireChangesToApply targets() {

        List<FireChangeToApply> list = params.getTargets().stream()
                .map(value -> (FireChangeToApply) new SimpleFireChangeToApply(value.getX(), value.getY(), false, true))
                .toList();

        return new FireAdditionsToApplyImpl(list);
    }


    /**
     * Implémentation basique de {@link FireChangesToApply}
     */
    class FireAdditionsToApplyImpl implements FireChangesToApply {

        private final Iterable<FireChangeToApply> values;

        public FireAdditionsToApplyImpl(Iterable<FireChangeToApply> values) {
            this.values = values;
        }

        @Override
        public int targetStep() {
            return 0;
        }

        @Override
        public FireChangesToApply firesToAddOnly() {
            return new FireAdditionsToApplyImpl(values);
        }

        @Override
        public FireChangesToApply firesToRemoveOnly() {
            return new FireAdditionsToApplyImpl(List.of());
        }

        @Override
        public Iterator<FireChangeToApply> iterator() {
            return values.iterator();
        }
    }


    /**
     * Implémentation basique de {@link FireChangeToApply}
     */
    class SimpleFireChangeToApply implements FireChangeToApply {

        private final int x;

        private final int y;

        private boolean toDeath;

        private boolean toBurn;

        public SimpleFireChangeToApply(int x, int y, boolean toDeath, boolean toBurn) {
            this.x = x;
            this.y = y;
            this.toDeath = toDeath;
            this.toBurn = toBurn;
        }

        @Override
        public boolean toDeath() {
            return toDeath;
        }

        @Override
        public boolean toBurn() {
            return toBurn;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }
}
