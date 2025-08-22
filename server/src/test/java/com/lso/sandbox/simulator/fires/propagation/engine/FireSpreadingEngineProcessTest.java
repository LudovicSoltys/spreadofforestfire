package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.propagation.Context;
import com.lso.sandbox.simulator.fires.propagation.calcul.FireSpreadingCalculator;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link FireSpreadingEngine#process(Iterable, Context)}
 */
class FireSpreadingEngineProcessTest {

    private FireSpreadingEngine subject;

    private BoardJpaCrudRepository mockBoardRepository;

    private FireSpreadingCalculator mockCalculator;

    private CellJpaQueryRepository mockQuery;

    private CellJpaCrudRepository mockRepository;

    @Test
    void should_check_something() {

        // then
        Assertions.fail("Not implemented yet...");
    }
}