package com.lso.sandbox.simulator.exposed.board.http.rest.api;

import com.lso.sandbox.simulator.shared.util.Mappable;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Range;

public class PostBoardRequest implements Mappable<PostBoardRequest> {

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

    @Valid
    public static class BoardChangeAttributes {
        @Range(min = 1, max = 10, message = "width should be between 1 included and 10 excluded")
        private byte width;

        @Range(min = 1, max = 10, message = "height should be between 1 included and 10 excluded")
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

}
