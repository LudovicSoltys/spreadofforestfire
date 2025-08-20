package com.lso.sandbox.simulator.infra.data.api;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.shared.util.IterableUtils;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CellJpaQueryRepository extends Repository<CellJpaEntity, Long> {

    Optional<CellJpaEntity> findByXAndY(int x, int y);

    default List<CellJpaEntity> findAllByCoordinates(Iterable<Coordinates> values) {
        return IterableUtils.streamOf(values)
                .map(value -> this.findByXAndY(value.x(), value.y()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }


    default boolean isAlive(int x, int y) {
        return findByXAndY(x, y).filter(CellJpaEntity::isAlive).isPresent();
    }

    List<CellJpaEntity> findByBurntAtIsNotNull();

    List<CellJpaEntity> findByBurntAtEquals(int value);

    List<CellJpaEntity> findByDeadAtIsNotNull();
}
