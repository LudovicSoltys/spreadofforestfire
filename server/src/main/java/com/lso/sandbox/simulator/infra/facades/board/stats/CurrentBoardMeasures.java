package com.lso.sandbox.simulator.infra.facades.board.stats;

import java.util.Set;

public interface CurrentBoardMeasures {

    int size();

    boolean isEmpty();

    boolean containsKey(BoardStatsRequestor.MeasureMethod key);

    long get(BoardStatsRequestor.MeasureMethod key);

    Set<BoardStatsRequestor.MeasureMethod> keySet();

    Set<Entry<BoardStatsRequestor.MeasureMethod, Long>> entrySet();

    static CurrentBoardMeasures empty() {
        return new NoMeasures();
    }

    interface Entry<K, V> {
        BoardStatsRequestor.MeasureMethod getKey();

        Long getValue();
    }

    class NoMeasures implements CurrentBoardMeasures {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean containsKey(BoardStatsRequestor.MeasureMethod key) {
            return false;
        }

        @Override
        public long get(BoardStatsRequestor.MeasureMethod key) {
            return 0;
        }

        @Override
        public Set<BoardStatsRequestor.MeasureMethod> keySet() {
            return Set.of();
        }

        @Override
        public Set<Entry<BoardStatsRequestor.MeasureMethod, Long>> entrySet() {
            return Set.of();
        }
    }
}
