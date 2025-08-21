package com.lso.sandbox.simulator.repositories;

import org.springframework.data.repository.Repository;

public interface CellsStatsJpaQueryRepository extends Repository<CellJpaEntity, Long> {

    Integer countByBurntAtIsNull();

    Integer countByBurntAtIsNotNullAndDeadAtIsNull();

    Integer countByDeadAtIsNotNull();
}
