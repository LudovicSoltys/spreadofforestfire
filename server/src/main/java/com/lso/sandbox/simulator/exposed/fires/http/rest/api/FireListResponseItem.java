package com.lso.sandbox.simulator.exposed.fires.http.rest.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFire;

public interface FireListResponseItem {

    int getX();

    int getY();

    int getStep();

    boolean isAlive();

    boolean isBurning();

    boolean isDead();

    static FireListResponseItem of(OngoingFire data) {
        return new FireListResponseItemImpl(data);
    }

    class FireListResponseItemImpl implements FireListResponseItem {

        private final OngoingFire data;

        public FireListResponseItemImpl(OngoingFire data) {
            this.data = data;
        }

        @Override
        public int getX() {
            return data.getX();
        }

        @Override
        public int getY() {
            return data.getY();
        }

        @Override
        public int getStep() {
            return data.step();
        }

        @Override
        public boolean isAlive() {
            return false;
        }

        @Override
        public boolean isBurning() {
            return true;
        }

        @Override
        public boolean isDead() {
            return false;
        }
    }
}
