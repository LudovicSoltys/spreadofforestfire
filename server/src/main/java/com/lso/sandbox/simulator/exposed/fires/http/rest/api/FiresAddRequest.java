package com.lso.sandbox.simulator.exposed.fires.http.rest.api;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation.InBoundConstraint;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.validation.StillAliveConstraint;
import com.lso.sandbox.simulator.shared.Coordinates;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class FiresAddRequest implements FiresChangeRequest {

    private boolean dryRun = false;

    @NotEmpty(message = "Input target list cannot be empty.")
    private List<@Valid TargetItem> targets;

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    public List<TargetItem> getTargets() {
        return targets;
    }

    public void setTargets(List<TargetItem> targets) {
        this.targets = targets;
    }

    @InBoundConstraint
    @StillAliveConstraint
    public static class TargetItem implements Coordinates {

        private int x;

        private int y;

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
