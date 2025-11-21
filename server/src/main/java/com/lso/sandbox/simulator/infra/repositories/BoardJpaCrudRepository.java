package com.lso.sandbox.simulator.infra.repositories;

import org.springframework.data.repository.CrudRepository;

/**
 * Dépôt de données dédié aux opérations CRUD sur les {@link BoardJpaEntity}s
 */
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
