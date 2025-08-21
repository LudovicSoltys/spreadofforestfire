package com.lso.sandbox.simulator.repositories;

import org.springframework.data.repository.CrudRepository;

public interface BoardJpaCrudRepository extends CrudRepository<BoardJpaEntity, Long> {

    default BoardJpaEntity first() {
        return findAll().iterator().next();
    }

    default boolean isEmpty() {
        if (count() == 0) {
            return true;
        }

        BoardJpaEntity entity = first();
        return entity.getHeight() == 0 || entity.getWidth() == 0;
    }
}
