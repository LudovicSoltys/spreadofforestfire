package com.lso.sandbox.simulator.infra.data.api;

import org.springframework.data.repository.Repository;

public interface CellsStatsJpaQueryRepository extends Repository<CellJpaEntity, Long> {

    Integer countByBurntAtIsNull();

    Integer countByBurntAtIsNotNullAndDeadAtIsNull();

    Integer countByDeadAtIsNotNull();
}
