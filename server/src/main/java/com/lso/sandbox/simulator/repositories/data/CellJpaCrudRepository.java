package com.lso.sandbox.simulator.repositories.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Dépôt de données dédié aux opérations CRUD sur les {@link CellJpaEntity}s
 */
public interface CellJpaCrudRepository extends CrudRepository<CellJpaEntity, Long> {


}
