package com.lso.sandbox.simulator.infra.facades.board.stats;

public interface StatsQuery {

    Iterable<BoardStatsRequestor.MeasureMethod> methods();
}
