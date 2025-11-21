package com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.internal;

import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireChangesApplied;
import com.lso.sandbox.simulator.exposed.fires.http.rest.internal.domain.add.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.infra.facades.fire.changes.api.FiresRegistrar;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests sur {@link FiresIgnitionService#execute(FireIgnitionUseCase.Input, FireIgnitionUseCase.Output)}
 */
class FiresIgnitionServiceExecuteTest {

    private FiresIgnitionService subject;

    private FiresRegistrar mockRegistror = Mockito.mock(FiresRegistrar.class);

    @BeforeEach
    void setUp() {

        subject = new FiresIgnitionService(mockRegistror);
    }

    @Test
    void should_delegate_to_registror_on_success() {

        // given
        FireChangesApplied expected = Mockito.mock(FireChangesApplied.class);
        Mockito.when(mockRegistror.saveAll(Mockito.any())).thenReturn(Either.right(expected));

        FireIgnitionUseCase.Input mockInput = Mockito.mock(FireIgnitionUseCase.Input.class);
        FireIgnitionUseCase.Output mockOutput = Mockito.mock(FireIgnitionUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockRegistror).saveAll(ArgumentMatchers.any());
        Mockito.verify(mockOutput).accept(ArgumentMatchers.any());
        Mockito.verify(mockOutput, Mockito.never()).reject(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockRegistror, mockOutput);
    }

    @Test
    void should_delegate_to_registror_on_failure() {

        // given
        Errors expected = Mockito.mock(Errors.class);
        Mockito.when(mockRegistror.saveAll(Mockito.any())).thenReturn(Either.left(expected));

        FireIgnitionUseCase.Input mockInput = Mockito.mock(FireIgnitionUseCase.Input.class);
        FireIgnitionUseCase.Output mockOutput = Mockito.mock(FireIgnitionUseCase.Output.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockRegistror).saveAll(ArgumentMatchers.any());
        Mockito.verify(mockOutput).reject(expected);
        Mockito.verify(mockOutput, Mockito.never()).accept(ArgumentMatchers.any());

        Mockito.verifyNoMoreInteractions(mockRegistror, mockOutput);
    }
}