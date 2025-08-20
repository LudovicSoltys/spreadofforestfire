package com.lso.sandbox.simulator.exposed.http.controllers.board.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.api.BoardRestEndpoint;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.AvailableBoard;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.spi.BoardRegistror;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.Rectangle;
import com.lso.sandbox.simulator.exposed.http.shared.Message;
import com.lso.sandbox.simulator.shared.validation.Errors;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class BoardRestController implements BoardRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(BoardRestController.class);

    private final BoardRetrievalUseCase retrieveUseCase;

    private final BoardUpdatingUseCase updateUseCase;

    public BoardRestController(BoardRetrievalUseCase retrieveUseCase, BoardUpdatingUseCase updateUseCase) {
        this.retrieveUseCase = retrieveUseCase;
        this.updateUseCase = updateUseCase;
    }

    @Override
    public PostBoardResponse changeBoard(@RequestBody @Valid PostBoardRequest request) {

        LOG.debug("Message=Trying to change board;Parameters={};", request);

        BoardUpdatingUseCase.Input input = new PostBoardInput(request);
        PostBoardOutput output = new PostBoardOutput();

        updateUseCase.execute(input, output);

        LOG.debug("Message=Operation of change is done;Result={};", output);

        return output;
    }

    @Override
    public BoardGetResponse getBoard() {

        LOG.debug("Message=Trying to retrieve current board;");

        BoardRetrievalUseCase.Input input = new GetBoardInput();
        BoardGetOutput output = new BoardGetOutput();

        retrieveUseCase.execute(input, output);

        LOG.debug("Message=Operation of retrieval of the board is done;Result={};", output);

        return output;
    }

    static class PostBoardInput implements BoardUpdatingUseCase.Input {

        private final Rectangle params;

        public PostBoardInput(PostBoardRequest params) {
            this.params = new PostBoardInputSize(params.getAttributes());
        }

        @Override
        public Rectangle getAttributes() {
            return params;
        }
    }

    static class PostBoardInputSize implements Rectangle {

        private final BoardChangeAttributes attributes;

        public PostBoardInputSize(BoardChangeAttributes attributes) {
            this.attributes = attributes;
        }

        @Override
        public byte getWidth() {
            return attributes.getWidth();
        }

        @Override
        public byte getHeight() {
            return attributes.getHeight();
        }
    }

    static class PostBoardOutput implements PostBoardResponse, BoardUpdatingUseCase.Output {
        @Override
        public List<Message> getMessages() {
            return List.of();
        }

        @Override
        public List<Message> getErrors() {
            return List.of();
        }

        @Override
        public void accept(BoardRegistror.CreatedBoard data) {

        }

        @Override
        public void reject(Errors errors) {

        }
    }

    static class GetBoardInput implements BoardRetrievalUseCase.Input {

    }

    static class BoardGetOutput implements BoardGetResponse, BoardRetrievalUseCase.Output {

        private AvailableBoard data = AvailableBoard.empty();

        private boolean exists = false;

        private List<Message> messages = new ArrayList<>();

        private List<Message> errors = new ArrayList<>();

        @Override
        public boolean isPresent() {
            return exists;
        }

        @Override
        public long getWidth() {
            return data.getWidth();
        }

        @Override
        public long getHeight() {
            return data.getHeight();
        }

        @Override
        public int getCurrentStep() {
            return 0;
        }

        @Override
        public boolean isGreen() {
            return data.greenCount() == getWidth() * getHeight();
        }

        @Override
        public boolean isBurning() {
            return data.burningCount() > 0;
        }

        @Override
        public boolean isDead() {
            return data.deadCount() ==  data.getWidth() * data.getHeight();
        }

        @Override
        public List<Message> getMessages() {
            return List.of();
        }

        @Override
        public List<Message> getErrors() {
            return List.of();
        }

        @Override
        public void accept(AvailableBoard item) {
            Objects.requireNonNull(item);
            data = item;
            exists = data.getHeight() > 0 && data.getWidth() > 0;

            if (!exists) {
                messages.add(Message.of("board.missing"));
            }
        }

        @Override
        public void reject(Errors errors) {

            errors.getAllErrors().forEach(error -> {
                this.errors.add(Message.of(error.getCode(), error.getArguments()));
            });
        }
    }

}
