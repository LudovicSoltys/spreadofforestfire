package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesToApply;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.FiresPropagationService;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.propagation.internal.engine.api.FireSpreadingCalculator;
import com.lso.sandbox.simulator.infra.facades.board.read.api.CurrentBoardSupplier;
import com.lso.sandbox.simulator.infra.facades.board.read.api.OneSingleBoard;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.infra.facades.fire.query.OngoingFiresInventory;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests sur {@link FiresPropagationService#execute(FirePropagationUseCase.Input, FirePropagationUseCase.Output)}
 */
class FiresPropagationServiceExecuteTest {

    private FiresPropagationService subject;

    private final CurrentBoardSupplier mockSupplier = Mockito.mock(CurrentBoardSupplier.class);

    private final OngoingFiresInventory mockInventory = Mockito.mock(OngoingFiresInventory.class);

    private final FireSpreadingCalculator mockEngine = Mockito.mock(FireSpreadingCalculator.class);

    private final FiresRegistrar mockRegistror = Mockito.mock(FiresRegistrar.class);

    @BeforeEach
    void setUp() {

        subject = new FiresPropagationService(mockSupplier, mockInventory, mockEngine, mockRegistror);
    }

    @Test
    void should_delegate_to_supplier_on_failure() {

        // given
        Errors expected = Mockito.mock(Errors.class);
        Mockito.when(mockSupplier.get()).thenReturn(Either.left(expected));

        FirePropagationUseCase.Input mockInput = Mockito.mock(FirePropagationUseCase.Input.class);
        FirePropagationUseCase.Output mockOutput = Mockito.mock(FirePropagationUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).get();
        Mockito.verify(mockOutput).reject(expected);
        Mockito.verify(mockOutput, Mockito.never()).accept(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
        Mockito.verifyNoInteractions(mockInventory, mockRegistror, mockEngine);
    }

    @Test
    void should_delegate_on_success() {

        // given
        OneSingleBoard mockBoard = Mockito.mock(OneSingleBoard.class);
        Mockito.when(mockBoard.currentStep()).thenReturn(RandomUtils.randomInt(1, 10));
        Mockito.when(mockSupplier.get()).thenReturn(Either.right(mockBoard));

        OngoingFires mockFires = Mockito.mock(OngoingFires.class);
        Mockito.when(mockFires.isEmpty()).thenReturn(true);
        Mockito.when(mockInventory.findAll()).thenReturn(Either.right(mockFires));

        Mockito.when(mockEngine.process(Mockito.any())).thenReturn(Either.right(Mockito.mock(FireChangesToApply.class)));

        FireChangesApplied mockChanges = Mockito.mock(FireChangesApplied.class);
        Mockito.when(mockRegistror.saveAll(Mockito.any())).thenReturn(Either.right(mockChanges));

        FirePropagationUseCase.Input mockInput = Mockito.mock(FirePropagationUseCase.Input.class);
        FirePropagationUseCase.Output mockOutput = Mockito.mock(FirePropagationUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).get();

        Mockito.verify(mockOutput).accept(ArgumentMatchers.any());
        Mockito.verify(mockOutput, Mockito.never()).reject(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockOutput);
    }
}