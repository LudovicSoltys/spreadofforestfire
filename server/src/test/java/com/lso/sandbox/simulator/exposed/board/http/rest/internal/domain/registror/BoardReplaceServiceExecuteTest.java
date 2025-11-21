package com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.Rectangle;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.internal.BoardReplaceService;
import com.lso.sandbox.simulator.infra.facades.board.register.api.BoardRegistrar;
import com.lso.sandbox.simulator.infra.facades.board.register.api.BoardToCreate;
import com.lso.sandbox.simulator.infra.facades.board.register.api.CreatedBoard;
import com.lso.sandbox.simulator.shared.util.Either;
import com.lso.sandbox.simulator.shared.validation.Errors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * Tests de {@link BoardReplaceService#execute(BoardUpdatingUseCase.Input, BoardUpdatingUseCase.Output)}
 */
class BoardReplaceServiceExecuteTest {

    private BoardReplaceService subject;

    private BoardRegistrar mockRegistror = Mockito.mock(BoardRegistrar.class);

    @BeforeEach
    void setUp() {

        subject = new BoardReplaceService(mockRegistror);
    }

    @Test
    void should_delegate_to_registror_when_operation_succeeds() {

        // given
        BoardUpdatingUseCase.Input mockInput = Mockito.mock(BoardUpdatingUseCase.Input.class);
        Mockito.when(mockInput.getAttributes()).thenReturn(mockAttributes());
        Mockito.when(mockInput.map(Mockito.any())).thenReturn(Mockito.mock(BoardToCreate.class));

        BoardUpdatingUseCase.Output mockOutput = Mockito.mock(BoardUpdatingUseCase.Output.class);

        Mockito.when(mockRegistror.save(Mockito.any())).thenReturn(Either.right(Mockito.mock(CreatedBoard.class)));

        ArgumentCaptor<BoardToCreate> savingBoardCaptor = ArgumentCaptor.forClass(BoardToCreate.class);

        // when
        subject.execute(mockInput, mockOutput);

        // then
        Mockito.verify(mockRegistror).deleteAll();

        Mockito.verify(mockRegistror).save(savingBoardCaptor.capture());
        BoardToCreate actualValue = savingBoardCaptor.getValue();
        Assertions.assertThat(actualValue).isNotNull();

        Mockito.verify(mockOutput).accept(ArgumentMatchers.any(CreatedBoard.class));
        Mockito.verify(mockOutput, Mockito.never()).reject(ArgumentMatchers.any(Errors.class));

        Mockito.verifyNoMoreInteractions(mockRegistror);
    }

    private static Rectangle mockAttributes() {
        return new Rectangle() {
            @Override
            public byte getWidth() {
                return 1;
            }

            @Override
            public byte getHeight() {
                return 2;
            }
        };
    }
}