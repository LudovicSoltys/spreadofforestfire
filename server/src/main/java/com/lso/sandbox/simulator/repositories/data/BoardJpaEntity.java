package com.lso.sandbox.simulator.repositories.data;

import com.lso.sandbox.simulator.shared.util.Mappable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Range;

/**
 * Entité JPA des attributs du plateau de la simulation
 */
@Entity
@Table(name="boards")
public class BoardJpaEntity implements Mappable<BoardJpaEntity> {

    // PrimaryKey
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(0)
    @Max(100)
    @Range(min= 0, max= 100)
    private byte height;

    @Min(0)
    @Max(100)
    @Range(min= 0, max= 100)
    private byte width;

    @Min(0)
    private int lastStep = 0;

    public BoardJpaEntity() {
    }

    public BoardJpaEntity(byte width, byte height) {
        this.height = height;
        this.width = width;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getHeight() {
        return height;
    }

    public void setHeight(byte height) {
        this.height = height;
    }

    public byte getWidth() {
        return width;
    }

    public void setWidth(byte width) {
        this.width = width;
    }

    public int getLastStep() {
        return lastStep;
    }

    public void setLastStep(int lastStep) {
        this.lastStep = lastStep;
    }

    public void incrementLastStep() {
        this.lastStep++;
    }
}
