package com.lso.sandbox.simulator.infra.data.api;

import org.springframework.data.repository.CrudRepository;

public interface BoardJpaCrudRepository extends CrudRepository<BoardJpaEntity, Long> {

    default BoardJpaEntity first() {
        return findById(1L).orElseThrow();
    }

    default boolean isEmpty() {
        if (count() == 0) {
            return true;
        }

        BoardJpaEntity entity = first();
        return entity.getHeight() == 0 || entity.getWidth() == 0;
    }
}
