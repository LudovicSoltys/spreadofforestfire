package com.lso.sandbox.simulator.fires.propagation;

import com.lso.sandbox.simulator.board.supplier.AvailableBoard;
import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.fires.add.facade.CellChangesApplied;
import com.lso.sandbox.simulator.fires.add.facade.FiresRegistror;
import com.lso.sandbox.simulator.fires.list.facade.OngoingFiresInventory;
import com.lso.sandbox.simulator.fires.propagation.engine.FireSpreadingProcessor;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import com.lso.sandbox.simulator.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

/**
 * Tests sur {@link FiresPropagationService#execute(FirePropagationUseCase.Input, FirePropagationUseCase.Output)}
 */
class FiresPropagationServiceExecuteTest {

    private FiresPropagationService subject;

    private CurrentBoardSupplier mockSupplier = Mockito.mock(CurrentBoardSupplier.class);

    private OngoingFiresInventory mockInventory = Mockito.mock(OngoingFiresInventory.class);

    private FireSpreadingProcessor mockEngine = Mockito.mock(FireSpreadingProcessor.class);

    private FiresRegistror mockRegistror = Mockito.mock(FiresRegistror.class);

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
        AvailableBoard mockBoard = Mockito.mock(AvailableBoard.class);
        Mockito.when(mockBoard.currentStep()).thenReturn(RandomUtils.randomInt(1, 10));
        Mockito.when(mockSupplier.get()).thenReturn(Either.right(mockBoard));

        OngoingFiresInventory.OngoingFires mockFires = Mockito.mock(OngoingFiresInventory.OngoingFires.class);
        Mockito.when(mockFires.getItems()).thenReturn(List.of());
        Mockito.when(mockFires.isEmpty()).thenReturn(true);
        Mockito.when(mockInventory.findAll()).thenReturn(Either.right(mockFires));

        Mockito.when(mockEngine.process(Mockito.any(), Mockito.any())).thenReturn(Either.right(List.of()));

        CellChangesApplied mockChanges = Mockito.mock(CellChangesApplied.class);
        Mockito.when(mockRegistror.saveAll(Mockito.any())).thenReturn(Either.right(List.of(mockChanges)));

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