package com.lso.sandbox.simulator.infra.facades.board.read.api;

import com.lso.sandbox.simulator.shared.util.Mappable;

/**
 * Informations sur le plateau de la simulation
 */
public interface OneSingleBoard extends Mappable<OneSingleBoard> {

    long getWidth();

    long getHeight();

    int currentStep();

    /**
     *
     * @return un plateau vide
     */
    static OneSingleBoard empty() {

        return new OneSingleBoard() {

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
        };
    }
}
