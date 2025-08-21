package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.board.registror.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.board.registror.Rectangle;

class PostBoardInput implements BoardUpdatingUseCase.Input {

    private final Rectangle params;

    public PostBoardInput(PostBoardRequest params) {
        this.params = new PostBoardInputSize(params.getAttributes());
    }

    @Override
    public Rectangle getAttributes() {
        return params;
    }

    static class PostBoardInputSize implements Rectangle {

        private final PostBoardRequest.BoardChangeAttributes attributes;

        public PostBoardInputSize(PostBoardRequest.BoardChangeAttributes attributes) {
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
}
