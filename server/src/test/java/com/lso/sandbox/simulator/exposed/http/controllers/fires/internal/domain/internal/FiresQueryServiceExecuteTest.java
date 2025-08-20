package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.api.CellsListUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.internal.CellsListService;
import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.spi.AllCellsSupplier;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.OngoingFiresInventory;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests for {@link FiresQueryService#execute(FireRetrievalUseCase.Input, FireRetrievalUseCase.Output)}
 */
class FiresQueryServiceExecuteTest {

    private FiresQueryService subject;

    private OngoingFiresInventory mockSupplier = Mockito.mock(OngoingFiresInventory.class);

    @BeforeEach
    void setUp() {

        subject = new FiresQueryService(mockSupplier);
    }

    @Test
    void should_delegate_to_supplier_on_success() {

        // given
        OngoingFiresInventory.OngoingFires expected = Mockito.mock(OngoingFiresInventory.OngoingFires.class);
        Mockito.when(mockSupplier.findAll()).thenReturn(Either.right(expected));

        FireRetrievalUseCase.Input mockInput = Mockito.mock(FireRetrievalUseCase.Input.class);
        FireRetrievalUseCase.Output mockOutput = Mockito.mock(FireRetrievalUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).findAll();
        Mockito.verify(mockOutput).accept(ArgumentMatchers.any());
        Mockito.verify(mockOutput, Mockito.never()).reject(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
    }

    @Test
    void should_delegate_to_supplier_on_failure() {

        // given
        Errors expected = Mockito.mock(Errors.class);
        Mockito.when(mockSupplier.findAll()).thenReturn(Either.left(expected));

        FireRetrievalUseCase.Input mockInput = Mockito.mock(FireRetrievalUseCase.Input.class);
        FireRetrievalUseCase.Output mockOutput = Mockito.mock(FireRetrievalUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockSupplier).findAll();
        Mockito.verify(mockOutput).reject(expected);
        Mockito.verify(mockOutput, Mockito.never()).accept(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockSupplier, mockOutput);
    }
}