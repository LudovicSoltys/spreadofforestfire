package com.lso.sandbox.simulator.exposed.http.controllers.board.api;

import com.lso.sandbox.simulator.exposed.http.shared.Response;
import com.lso.sandbox.simulator.shared.util.Mappable;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/board")
public interface BoardRestEndpoint {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    PostBoardResponse changeBoard(@RequestBody @Valid BoardRestEndpoint.PostBoardRequest request);

    @GetMapping
    BoardGetResponse getBoard();

    enum BoardChangeMethods {
        RESET
    }

    @Valid
    class BoardChangeAttributes {
        @Range(min = 0, max = 10, message = "width should be between 0 and 10")
        private byte width;

        @Range(min = 0, max = 10, message = "height should be between 0 and 10")
        private byte height;

        public byte getWidth() {
            return width;
        }

        public void setWidth(byte width) {
            this.width = width;
        }

        public byte getHeight() {
            return height;
        }

        public void setHeight(byte height) {
            this.height = height;
        }
    }

    class PostBoardRequest implements Mappable<PostBoardRequest> {

        @Valid
        private BoardChangeAttributes attributes;

        public PostBoardRequest() {
        }

        public BoardChangeAttributes getAttributes() {
            return attributes;
        }

        public void setAttributes(BoardChangeAttributes attributes) {
            this.attributes = attributes;
        }
    }

    interface PostBoardResponse extends Response {

    }

    interface BoardGetResponse extends Response {

        boolean isPresent();

        long getWidth();

        long getHeight();

        int getCurrentStep();

        boolean isGreen();

        boolean isBurning();

        boolean isDead();
    }
}
