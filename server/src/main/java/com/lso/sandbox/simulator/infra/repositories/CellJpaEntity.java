package com.lso.sandbox.simulator.infra.repositories;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

/**
 * Entité JPA de description d'une partie élémentaire du plateau de la simulation
 */
@Entity
@Table(name = "cells")
public class CellJpaEntity {

    // PrimaryKey
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(0)
    @Max(100)
    @Range(min = 0, max = 100)
    private int x;

    @Min(0)
    @Max(100)
    @Range(min = 0, max = 100)
    private int y;

    @Min(0)
    @Max(100)
    @Range(min = 0, max = 100)
    private int flammability;

    @Min(0)
    private Integer burntAt;

    @Min(0)
    private Integer deadAt;

    public CellJpaEntity() {
    }

    public CellJpaEntity(byte x, byte y, int f) {
        this.x = x;
        this.y = y;
        this.flammability = f;
    }

    public long getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(byte x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(byte y) {
        this.y = y;
    }

    public Integer getBurntAt() {
        return burntAt;
    }

    public void setBurntAt(Integer burntAt) {
        this.burntAt = burntAt;
    }

    public Integer getDeadAt() {
        return deadAt;
    }

    public void setDeadAt(Integer deadAt) {
        this.deadAt = deadAt;
    }

    public boolean isAlive() {
        return burntAt == null && deadAt == null;
    }

    public boolean isBurning() {
        return burntAt != null && deadAt == null;
    }

    public boolean isDead() {
        return deadAt != null;
    }

    public int getFlammability() {
        return flammability;
    }

    public void setFlammability(int flammability) {
        this.flammability = flammability;
    }
}
