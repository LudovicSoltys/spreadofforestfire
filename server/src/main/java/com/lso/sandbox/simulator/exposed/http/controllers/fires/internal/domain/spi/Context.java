package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi;

public interface Context {

    int originalStep();

    int targetStep();

    static Context of(int originalStep, int targetStep) {
        return new SimpleContext(originalStep, targetStep);
    }

    static Context next(int originalStep) {
        return new SimpleContext(originalStep, originalStep + 1);
    }

    class SimpleContext implements Context {

        private final int originalStep;

        private final int targetStep;

        public SimpleContext(int originalStep, int targetStep) {
            this.originalStep = originalStep;
            this.targetStep = targetStep;
        }

        @Override
        public int originalStep() {
            return originalStep;
        }

        @Override
        public int targetStep() {
            return targetStep;
        }
    }
}
