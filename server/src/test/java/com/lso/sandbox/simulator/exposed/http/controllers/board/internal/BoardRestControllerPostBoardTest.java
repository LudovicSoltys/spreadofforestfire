package com.lso.sandbox.simulator.exposed.http.controllers.board.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.exposed.http.controllers.board.api.BoardRestEndpoint;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardUpdatingUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de la fonction {@link BoardRestController#changeBoard(BoardRestEndpoint.PostBoardRequest)}
 */
@WebMvcTest(controllers = BoardRestController.class)
class BoardRestControllerPostBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardRetrievalUseCase retrieveUseCase;

    @MockitoBean
    private BoardUpdatingUseCase updateUseCase;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_return_some_content_with_http_code_200() throws Exception {

        // given
        BoardRestEndpoint.PostBoardRequest requestBody = mockRequestBody((byte)1, (byte)1);
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void should_return_error_message_with_http_code_400() throws Exception {

        // given
        BoardRestEndpoint.PostBoardRequest requestBody = mockRequestBody((byte) -1, (byte) -1);
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    static private BoardRestEndpoint.PostBoardRequest mockRequestBody(byte w, byte h) {
        return new PostBoardRequestImpl(w, h);
    }

    static class BoardChangeAttributesImpl extends BoardRestEndpoint.BoardChangeAttributes {

        private final byte w;

        private final byte h;

        public BoardChangeAttributesImpl(byte w, byte h) {
            this.w = w;
            this.h = h;
        }

        @Override
        public byte getWidth() {
            return w;
        }

        @Override
        public byte getHeight() {
            return h;
        }
    }

    static class PostBoardRequestImpl extends BoardRestEndpoint.PostBoardRequest {

        private final BoardRestEndpoint.BoardChangeAttributes attributes;

        public PostBoardRequestImpl(byte w, byte h) {
            this.attributes = new BoardChangeAttributesImpl(w, h);
        }

        @Override
        public BoardRestEndpoint.BoardChangeAttributes getAttributes() {
            return attributes;
        }
    }
}