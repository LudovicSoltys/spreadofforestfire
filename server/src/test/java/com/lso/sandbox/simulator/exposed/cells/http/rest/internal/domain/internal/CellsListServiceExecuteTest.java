package com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.cells.http.rest.internal.domain.api.CellsListUseCase;
import com.lso.sandbox.simulator.infra.facades.cells.list.api.AllCellsSupplier;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests for {@link CellsListService#execute(CellsListUseCase.Input, CellsListUseCase.Output)}
 */
class CellsListServiceExecuteTest {

    private CellsListService subject;

    private AllCellsSupplier mockSupplier = Mockito.mock(AllCellsSupplier.class);

    @BeforeEach
    void setUp() {

        subject = new CellsListService(mockSupplier);
    }

    @Test
    void should_delegate_to_supplier_on_success() {

        // given
        AllCellsSupplier.AllCells expected = Mockito.mock(AllCellsSupplier.AllCells.class);
        Mockito.when(mockSupplier.get()).thenReturn(Either.right(expected));

        CellsListUseCase.Input mockInput = Mockito.mock(CellsListUseCase.Input.class);
        CellsListUseCase.Output mockOutput = Mockito.mock(CellsListUseCase.Output.class);

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

        CellsListUseCase.Input mockInput = Mockito.mock(CellsListUseCase.Input.class);
        CellsListUseCase.Output mockOutput = Mockito.mock(CellsListUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).get();
        Mockito.verify(mockOutput).reject(expected);
        Mockito.verify(mockOutput, Mockito.never()).accept(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
    }
}