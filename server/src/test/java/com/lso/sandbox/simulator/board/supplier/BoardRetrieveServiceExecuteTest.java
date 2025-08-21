package com.lso.sandbox.simulator.board.supplier;

import com.lso.sandbox.simulator.board.supplier.facade.CurrentBoardSupplier;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests de {@link BoardRetrieveService#execute(BoardRetrievalUseCase.Input, BoardRetrievalUseCase.Output)}
 */
class BoardRetrieveServiceExecuteTest {

    private BoardRetrieveService subject;

    private CurrentBoardSupplier mockSupplier = Mockito.mock(CurrentBoardSupplier.class);

    @BeforeEach
    void setUp() {

        subject = new BoardRetrieveService(mockSupplier);
    }

    @Test
    void should_delegate_to_supplier_on_success() {

        // given
        AvailableBoard expected = Mockito.mock(AvailableBoard.class);
        Mockito.when(mockSupplier.get()).thenReturn(Either.right(expected));

        BoardRetrievalUseCase.Input mockInput = Mockito.mock(BoardRetrievalUseCase.Input.class);
        BoardRetrievalUseCase.Output mockOutput = Mockito.mock(BoardRetrievalUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).get();
        Mockito.verify(mockOutput).accept(expected);
        Mockito.verify(mockOutput, Mockito.never()).reject(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
    }

    @Test
    void should_delegate_to_supplier_on_failure() {

        // given
        Errors expected = Mockito.mock(Errors.class);
        Mockito.when(mockSupplier.get()).thenReturn(Either.left(expected));

        BoardRetrievalUseCase.Input mockInput = Mockito.mock(BoardRetrievalUseCase.Input.class);
        BoardRetrievalUseCase.Output mockOutput = Mockito.mock(BoardRetrievalUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).get();
        Mockito.verify(mockOutput).reject(expected);
        Mockito.verify(mockOutput, Mockito.never()).accept(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
    }
}