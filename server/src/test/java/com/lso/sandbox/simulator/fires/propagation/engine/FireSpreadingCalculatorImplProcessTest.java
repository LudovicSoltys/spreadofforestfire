package com.lso.sandbox.simulator.fires.propagation.engine;

import com.lso.sandbox.simulator.fires.add.FireChangesToApply;
import com.lso.sandbox.simulator.fires.list.OngoingFire;
import com.lso.sandbox.simulator.fires.propagation.FireToPropagate;
import com.lso.sandbox.simulator.fires.propagation.FiresToPropagate;
import com.lso.sandbox.simulator.fires.propagation.NextFire;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Iterator;
import java.util.List;

/**
 * Tests for {@link FireSpreadingCalculator#process(FiresToPropagate)}
 */
class FireSpreadingCalculatorImplProcessTest {

    private FireSpreadingCalculator subject;

    private final CellJpaQueryRepository repository = Mockito.mock(CellJpaQueryRepository.class);

    @BeforeEach
    void setUp() {
        subject = new FireSpreadingCalculatorImpl(repository);
    }

    @Test
    void should_return_some_result_when_cell_is_alive() {

        // given
        Mockito.when(repository.isAlive(1, 1)).thenReturn(true);

        FiresToPropagate input = mockFireToPropagate(List.of(mockFireToPropagate(1, 2)), List.of(mockNextFire(1, 1)));

        // when
        Either<Errors, FireChangesToApply> result = subject.process(input);

        // then
        Assertions.assertThat(result.isRight()).isTrue();

        FireChangesToApply actualValues = result.get();
        Assertions.assertThat(actualValues.size()).isEqualTo(2);
    }

    @Test
    void should_return_some_result_when_cell_is_not_alive() {

        // given
        Mockito.when(repository.isAlive(1, 1)).thenReturn(false);

        FiresToPropagate input = mockFireToPropagate(List.of(mockFireToPropagate(1, 2)), List.of(mockNextFire(1, 1)));

        // when
        Either<Errors, FireChangesToApply> result = subject.process(input);

        // then
        Assertions.assertThat(result.isRight()).isTrue();

        FireChangesToApply actualValues = result.get();
        Assertions.assertThat(actualValues.size()).isEqualTo(1);
    }

    private static NextFire mockNextFire(int x, int y) {
        return NextFire.of(1, x, y);
    }

    private static FireToPropagate mockFireToPropagate(int x, int y) {
        return FireToPropagate.of(1, new OngoingFire() {
            @Override
            public int step() {
                return 1;
            }

            @Override
            public int getX() {
                return x;
            }

            @Override
            public int getY() {
                return y;
            }
        });
    }

    private static FiresToPropagate mockFireToPropagate(List<FireToPropagate> items, List<NextFire> others) {

        return new FiresToPropagate() {
            @Override
            public int step() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return items.isEmpty();
            }

            @Override
            public Iterable<NextFire> around() {
                return others;
            }

            @Override
            public Iterator<FireToPropagate> iterator() {
                return items.iterator();
            }
        };
    }
}