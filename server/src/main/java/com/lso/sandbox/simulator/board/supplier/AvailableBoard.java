package com.lso.sandbox.simulator.board.supplier;

import com.lso.sandbox.simulator.shared.util.Mappable;

/**
 * Informations sur le plateau de la simulation
 */
public interface AvailableBoard extends Mappable<AvailableBoard> {

    long getWidth();

    long getHeight();

    int currentStep();

    int greenCount();

    int burningCount();

    int deadCount();

    /**
     *
     * @return un plateau vide
     */
    static AvailableBoard empty() {
        return new AvailableBoard() {

            @Override
            public int currentStep() {
                return -1;
            }

            @Override
            public long getWidth() {
                return 0;
            }

            @Override
            public long getHeight() {
                return 0;
            }

            @Override
            public int greenCount() {
                return 0;
            }

            @Override
            public int burningCount() {
                return 0;
            }

            @Override
            public int deadCount() {
                return 0;
            }
        };
    }
}
