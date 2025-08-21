package com.lso.sandbox.simulator.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class CellRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CellJpaCrudRepository cells;

    private final CellJpaEntity entity1 = new CellJpaEntity((byte) 2, (byte) 3);

    private final CellJpaEntity entity2 = new CellJpaEntity((byte) 20, (byte) 30);

    @BeforeEach
    void setUp() {
        // Add new cells to database
        cells.saveAll(List.of(entity1, entity2));
    }

    @Test
    void testCount() {

        // when
        long result = cells.count();

        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void testFindAll() {

        // when
        Iterable<CellJpaEntity> result = cells.findAll();

        // then
        Assertions.assertThat(result).isNotNull().hasSize(2);
    }
}