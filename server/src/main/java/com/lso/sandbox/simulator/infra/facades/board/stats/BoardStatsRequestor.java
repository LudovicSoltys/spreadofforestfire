package com.lso.sandbox.simulator.infra.facades.board.stats;

import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.Arrays;
import java.util.List;

public interface BoardStatsRequestor {

    default Iterable<MeasureMethod> availableMethods() {
        return List.of(MeasureMethod.values());
    };

    default Either<Errors, CurrentBoardMeasures> findAll() {
        StatsQuery query = () -> Arrays.asList(MeasureMethod.values());

        return findAll(query);
    }

    Either<Errors, CurrentBoardMeasures> findAll(StatsQuery query);

    enum MeasureMethod {
        COUNT_ALIVE_ONLY,
        COUNT_BURNING_ONLY,
        COUNT_DEAD_ONLY
    }
}
