package com.lso.sandbox.simulator.repositories.data;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Dépôt de données dédié à la consultation des {@link CellJpaEntity}s
 */
public interface CellJpaQueryRepository extends Repository<CellJpaEntity, Long> {

    Optional<CellJpaEntity> findByXAndY(int x, int y);

    default boolean isAlive(int x, int y) {
        return findByXAndY(x, y).filter(CellJpaEntity::isAlive).isPresent();
    }

    List<CellJpaEntity> findByBurntAtEquals(int value);
}
