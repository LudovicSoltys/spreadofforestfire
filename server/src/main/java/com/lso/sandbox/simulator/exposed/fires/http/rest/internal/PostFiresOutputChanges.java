package com.lso.sandbox.simulator.exposed.fires.http.rest.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangeApplied;

public class PostFiresOutputChanges {

    private boolean dead;

    private boolean burning;

    private int x;

    private int y;

    public PostFiresOutputChanges() {
    }

    public PostFiresOutputChanges(FireChangeApplied data) {
        this.x = data.getX();
        this.y = data.getY();
        this.dead = data.isDead();
        this.burning = data.isBurning();
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isBurning() {
        return burning;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
