package com.lso.sandbox.simulator.infra.facades.board.stats;

import com.lso.sandbox.simulator.infra.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.repositories.CellsStatsJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardStatsFacade implements BoardStatsRequestor {

    private final BoardJpaCrudRepository boards;

    private final CellsStatsJpaQueryRepository stats;

    public BoardStatsFacade(BoardJpaCrudRepository boards, CellsStatsJpaQueryRepository stats) {
        this.boards = boards;
        this.stats = stats;
    }

    @Override
    public Either<Errors, CurrentBoardMeasures> findAll(StatsQuery query) {

        if (boards.isEmpty()) {
            throw new NoSuchElementException("board.empty");
        }

        CurrentBoardMeasuresImpl result = new CurrentBoardMeasuresImpl();

        result.put(MeasureMethod.COUNT_ALIVE_ONLY, (long) stats.countByBurntAtIsNull());
        result.put(MeasureMethod.COUNT_BURNING_ONLY, (long) stats.countByBurntAtIsNotNullAndDeadAtIsNull());
        result.put(MeasureMethod.COUNT_DEAD_ONLY, (long) stats.countByDeadAtIsNotNull());

        return Either.right(result);
    }

    class CurrentBoardMeasuresImpl implements CurrentBoardMeasures {

        private final Map<MeasureMethod, Long> map = new HashMap<>();

        void put(MeasureMethod key, Long value) {
            map.put(key, value);
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public boolean containsKey(MeasureMethod key) {
            return map.containsKey(key);
        }

        @Override
        public long get(MeasureMethod key) {
            return map.getOrDefault(key, 0L);
        }

        @Override
        public Set<MeasureMethod> keySet() {
            return map.keySet();
        }

        @Override
        public Set<Entry<MeasureMethod, Long>> entrySet() {
            return map.entrySet().stream()
                    .map(SimpleEntry::new)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }

    static class SimpleEntry implements CurrentBoardMeasures.Entry<MeasureMethod, Long> {

        private final Map.Entry<MeasureMethod, Long> data;

        public SimpleEntry(Map.Entry<MeasureMethod, Long> data) {
            this.data = data;
        }

        @Override
        public MeasureMethod getKey() {
            return data.getKey();
        }

        @Override
        public Long getValue() {
            return data.getValue();
        }
    }
}
