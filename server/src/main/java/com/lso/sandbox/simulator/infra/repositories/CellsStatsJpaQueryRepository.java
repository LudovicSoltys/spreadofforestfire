package com.lso.sandbox.simulator.infra.repositories;

import org.springframework.data.repository.Repository;

/**
 * Dépôt de données dédié à la consultation des statistiques sur les {@link CellJpaEntity}s
 */
public interface CellsStatsJpaQueryRepository extends Repository<CellJpaEntity, Long> {

    Integer countByBurntAtIsNull();

    Integer countByBurntAtIsNotNullAndDeadAtIsNull();

    Integer countByDeadAtIsNotNull();
}
