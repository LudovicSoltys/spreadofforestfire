package com.lso.sandbox.simulator.board.registror;

import com.lso.sandbox.simulator.board.registror.facade.BoardRegistror;
import com.lso.sandbox.simulator.board.registror.facade.BoardToCreate;
import com.lso.sandbox.simulator.board.registror.facade.CreatedBoard;
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

    private BoardRegistror mockRegistror = Mockito.mock(BoardRegistror.class);

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