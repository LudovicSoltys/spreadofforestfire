package com.lso.sandbox.simulator.fires;

/**
 * Corps de la requête de propagation de l'incendie
 */
public class FiresPropagationRequest implements FiresChangeRequest {

    private boolean dryRun = false;

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }
}
