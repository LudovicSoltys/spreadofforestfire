package com.lso.sandbox.simulator.infra.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BoardJpaCrudRepository boards;

    private final BoardJpaEntity entity1 = new BoardJpaEntity((byte) 3, (byte) 2);

    private final BoardJpaEntity entity2 = new BoardJpaEntity((byte) 30, (byte) 20);

    @BeforeEach
    void setUp() {
        // Add new boards to database
        entityManager.persist(entity1);
        entityManager.persist(entity2);
    }

    @Test
    void testCount() {

        // when
        long result = boards.count();

        // then
        Assertions.assertThat(result).isEqualTo(2);
    }

    @Test
    void testFindAll() {

        // when
        Iterable<BoardJpaEntity> result = boards.findAll();

        // then
        Assertions.assertThat(result).isNotNull().hasSize(2);
    }
}