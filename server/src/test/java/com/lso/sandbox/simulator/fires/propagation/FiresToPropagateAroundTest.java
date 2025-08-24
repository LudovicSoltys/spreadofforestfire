package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.fires.list.OngoingFire;
import com.lso.sandbox.simulator.fires.list.OngoingFires;
import com.lso.sandbox.simulator.fires.shared.Coordinates;
import com.lso.sandbox.simulator.repositories.facades.fire.query.OngoingFiresFactory;
import com.lso.sandbox.simulator.utils.RandomUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Tests for {@link FiresToPropagate#around()}
 */
class FiresToPropagateAroundTest {

    @ParameterizedTest
    @MethodSource("data")
    void should_return_some_result(String description, AvailableBoard board, OngoingFires inputs, List<NextFire> expectedOutputs) {

        // given
        FiresToPropagate subject = FiresToPropagate.of(board, inputs);

        // when
        Iterable<NextFire> result = subject.around();

        // then
        Assertions.assertThat(result).as(description)
                .hasSize(expectedOutputs.size())
                .extracting(Coordinates::getX, Coordinates::getY)
                .containsAll(expectedOutputs.stream().map(coordinates -> Tuple.tuple(coordinates.getX(), coordinates.getY())).toList());
    }

    private static Stream<Arguments> data() {

        return Stream.of(
                Arguments.of(
                        "Empty board should generate no coordinates",
                        mockBoard(0, 0),
                        mockInputs(mockInput(1, 1)),
                        List.of()),

                Arguments.of(
                        "Basic board with only one single cell should generate no coordinates",
                        mockBoard(1, 1),
                        mockInputs(mockInput(1, 1)),
                        List.of()),

                Arguments.of(
                        "No input coordinates should generate no coordinates",
                        mockBoard(RandomUtils.randomInt(2, 10), RandomUtils.randomInt(2, 10)),
                        mockInputs(),
                        List.of()),

                Arguments.of(
                        "A row-only board should generate some coordinates #1",
                        mockBoard(10, 1),
                        mockInputs(mockInput(0, 0)),
                        List.of(mockExpectedOutput(1, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #2",
                        mockBoard(10, 1),
                        mockInputs(mockInput(9, 0)),
                        List.of(mockExpectedOutput(8, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #3",
                        mockBoard(10, 1),
                        mockInputs(mockInput(5, 0)),
                        List.of(mockExpectedOutput(4, 0), mockExpectedOutput(6, 0))),

                Arguments.of(
                        "A row-only board should generate some coordinates #4 (#1 + #2 + #3)",
                        mockBoard(10, 1),
                        mockInputs(mockInput(0, 0), mockInput(5, 0), mockInput(9, 0)),
                        List.of(mockExpectedOutput(1, 0), mockExpectedOutput(8, 0), mockExpectedOutput(4, 0), mockExpectedOutput(6, 0))),

                Arguments.of(
                        "A column-only board should generate some coordinates #1",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 0)),
                        List.of(mockExpectedOutput(0, 1))),

                Arguments.of(
                        "A column-only board should generate some coordinates #2",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 10)),
                        List.of(mockExpectedOutput(0, 9))),

                Arguments.of(
                        "A column-only board should generate some coordinates #3",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 5)),
                        List.of(mockExpectedOutput(0, 4), mockExpectedOutput(0, 6))),

                Arguments.of(
                        "A column-only board should generate some coordinates #4 (#1 + #2 + #3)",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 0), mockInput(0, 5), mockInput(0, 10)),
                        List.of(mockExpectedOutput(0, 1), mockExpectedOutput(0, 9), mockExpectedOutput(0, 4), mockExpectedOutput(0, 6))),

                Arguments.of(
                        "A column-only board should generate some coordinates #5",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 0), mockInput(0, 1)),
                        List.of(mockExpectedOutput(0, 2))),

                Arguments.of(
                        "A column-only board should generate some coordinates #6",
                        mockBoard(1, 10),
                        mockInputs(mockInput(0, 0), mockInput(0, 1), mockInput(0, 2)),
                        List.of(mockExpectedOutput(0, 3))),

                Arguments.of(
                        "A column-only board should generate some coordinates #7",
                        mockBoard(1, 3),
                        mockInputs(mockInput(0, 0), mockInput(0, 1), mockInput(0, 2)),
                        List.of()),

                Arguments.of(
                        "A column-only board should generate some coordinates #8",
                        mockBoard(1, 5),
                        mockInputs(mockInput(0, 1), mockInput(0, 2), mockInput(0, 3)),
                        List.of(mockExpectedOutput(0, 0), mockExpectedOutput(0, 4))),

                Arguments.of(
                        "A cell should generate some coordinates around it #1",
                        mockBoard(10, 10),
                        mockInputs(mockInput(5, 5)),
                        List.of(mockExpectedOutput(4, 5), mockExpectedOutput(6, 5), mockExpectedOutput(5, 4), mockExpectedOutput(5, 6))),

                Arguments.of(
                        "A cell should generate some coordinates around it #2",
                        mockBoard(10, 10),
                        mockInputs(mockInput(4, 5), mockInput(5, 5)),
                        List.of(
                                mockExpectedOutput(3, 5), mockExpectedOutput(6, 5),
                                mockExpectedOutput(4, 4), mockExpectedOutput(5, 4),
                                mockExpectedOutput(4, 6), mockExpectedOutput(5, 6)))
        );
    }

    private static AvailableBoard mockBoard(int w, int h) {
        AvailableBoard mock = Mockito.mock(AvailableBoard.class);
        Mockito.when(mock.getHeight()).thenReturn((long) h);
        Mockito.when(mock.getWidth()).thenReturn((long) w);
        Mockito.when(mock.currentStep()).thenReturn(RandomUtils.randomInt(1, 100));
        Mockito.when(mock.map(Mockito.any())).thenCallRealMethod();
        return mock;
    }

    private static OngoingFires mockInputs(OngoingFire... elements) {
        return OngoingFiresFactory.of(1, Arrays.asList(elements));
    }

    private static OngoingFire mockInput(int x, int y) {
        OngoingFire mock = Mockito.mock(OngoingFire.class);
        Mockito.when(mock.getX()).thenReturn(x);
        Mockito.when(mock.getY()).thenReturn(y);
        Mockito.when(mock.step()).thenReturn(1);
        return mock;
    }

    private static NextFire mockExpectedOutput(int x, int y) {
        NextFire mock = Mockito.mock(NextFire.class);
        Mockito.when(mock.getX()).thenReturn(x);
        Mockito.when(mock.getY()).thenReturn(y);
        Mockito.when(mock.step()).thenReturn(1);
        return mock;
    }
}