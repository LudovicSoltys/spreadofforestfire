package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.api.OngoingFires;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.list.internal.FiresQueryService;
import com.lso.sandbox.simulator.infra.facades.fire.query.OngoingFiresInventory;
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
        OngoingFires expected = Mockito.mock(OngoingFires.class);
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