package com.lso.sandbox.simulator.infra.engine.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Coordinates;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.FiresPropagationEngine;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.infra.data.api.BoardJpaEntity;
import com.lso.sandbox.simulator.infra.data.api.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.utils.RandomUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

class FireSpreadingCalculatorProcessTest {

    private FiresPropagationEngine subject;

    private final BoardJpaCrudRepository mockBoards = Mockito.mock(BoardJpaCrudRepository.class);

    private final CellJpaQueryRepository mockCells = Mockito.mock(CellJpaQueryRepository.class);

    @BeforeEach
    void setUp() {
        subject = new FireSpreadingCalculator(mockBoards, mockCells);
    }

    @Test
    void should_return_invalid_result_when_board_is_missing() {

        // given
        Mockito.when(mockBoards.isEmpty()).thenReturn(true);

        Iterable<Coordinates> input = List.of(Mockito.mock(Coordinates.class));

        // when
        Either<Errors, Iterable<Coordinates>> result = subject.process(input);

        // then
        Assertions.assertThat(result.isLeft()).isTrue();
        Mockito.verify(mockCells, Mockito.never()).findByXAndY(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    @ParameterizedTest
    @MethodSource("data")
    void should_return_some_result_when_board_is_valid(String description, BoardJpaEntity field, List<Coordinates> inputs, List<Coordinates> expectedOutputs) {

        // given
        Mockito.when(mockBoards.isEmpty()).thenReturn(false);
        Mockito.when(mockBoards.first()).thenReturn(field);

        Mockito.when(mockCells.isAlive(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // when
        Either<Errors, Iterable<Coordinates>> result = subject.process(inputs);

        // then
        Assertions.assertThat(result.isRight()).isTrue();
        Assertions.assertThat(result.get()).as(description)
                .hasSize(expectedOutputs.size())
                .extracting(Coordinates::x, Coordinates::y)
                .containsAll(expectedOutputs.stream().map(coordinates -> Tuple.tuple(coordinates.x(), coordinates.y())).toList());

        Mockito.verify(mockCells, Mockito.times(expectedOutputs.size())).isAlive(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt());
    }

    private static Stream<Arguments> data() {

        return Stream.of(
                Arguments.of(
                        "Empty board should generate no coordinates",
                        mockBoard(0, 0),
                        List.of(mockCoordinates(1, 1)),
                        List.of()),

                Arguments.of(
                        "Basic board with only one single cell should generate no coordinates",
                        mockBoard(1, 1),
                        List.of(mockCoordinates(1, 1)),
                        List.of()),

                Arguments.of(
                        "No input coordinates should generate no coordinates",
                        mockBoard(RandomUtils.randomInt(2, 10), RandomUtils.randomInt(2, 10)),
                        List.of(),
                        List.of()),

                Arguments.of(
                        "A row-only board should generate some coordinates #1",
                        mockBoard(10, 1),
                        List.of(mockCoordinates(0, 0)),
                        List.of(mockCoordinates(1, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #2",
                        mockBoard(10, 1),
                        List.of(mockCoordinates(9, 0)),
                        List.of(mockCoordinates(8, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #3",
                        mockBoard(10, 1),
                        List.of(mockCoordinates(5, 0)),
                        List.of(mockCoordinates(4, 0), mockCoordinates(6, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #4 (#1 + #2 + #3)",
                        mockBoard(10, 1),
                        List.of(mockCoordinates(0, 0), mockCoordinates(5, 0), mockCoordinates(9, 0)),
                        List.of(mockCoordinates(1, 0), mockCoordinates(8, 0), mockCoordinates(4, 0), mockCoordinates(6, 0))),


                Arguments.of(
                        "A column-only board should generate some coordinates #1",
                        mockBoard(1, 10),
                        List.of(mockCoordinates(0, 0)),
                        List.of(mockCoordinates(0, 1))),

                Arguments.of(
                        "A column-only board should generate some coordinates #2",
                        mockBoard(1, 10),
                        List.of(mockCoordinates(0, 10)),
                        List.of(mockCoordinates(0, 9))),

                Arguments.of(
                        "A column-only board should generate some coordinates #3",
                        mockBoard(1, 10),
                        List.of(mockCoordinates(0, 5)),
                        List.of(mockCoordinates(0, 4), mockCoordinates(0, 6))),

                Arguments.of(
                        "A column-only board should generate some coordinates #4 (#1 + #2 + #3)",
                        mockBoard(1, 10),
                        List.of(mockCoordinates(0, 0), mockCoordinates(0, 5), mockCoordinates(0, 10)),
                        List.of(mockCoordinates(0, 1), mockCoordinates(0, 9), mockCoordinates(0, 4), mockCoordinates(0, 6))),

                Arguments.of(
                        "A cell should generate some coordinates around it",
                        mockBoard(10, 10),
                        List.of(mockCoordinates(5, 5)),
                        List.of(mockCoordinates(4, 5), mockCoordinates(6, 5), mockCoordinates(5, 4), mockCoordinates(5, 6)))
        );
    }

    private static BoardJpaEntity mockBoard(int w, int h) {
        return new BoardJpaEntity((byte) w, (byte) h);
    }

    private static Coordinates mockCoordinates(int x, int y) {
        return Coordinates.of(x, y);
    }
}