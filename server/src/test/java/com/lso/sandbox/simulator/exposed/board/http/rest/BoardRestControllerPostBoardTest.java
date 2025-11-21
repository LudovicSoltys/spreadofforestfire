package com.lso.sandbox.simulator.exposed.board.http.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.exposed.board.http.rest.api.PostBoardRequest;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.BoardRestController;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
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
 * Tests de la fonction {@link BoardRestController#changeBoard(PostBoardRequest)}
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
        PostBoardRequest requestBody = mockRequestBody((byte)1, (byte)1);
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
        PostBoardRequest requestBody = mockRequestBody((byte) -1, (byte) -1);
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

    static private PostBoardRequest mockRequestBody(byte w, byte h) {

        PostBoardRequest.BoardChangeAttributes attributes = new PostBoardRequest.BoardChangeAttributes();
        attributes.setHeight(h);
        attributes.setWidth(w);

        PostBoardRequest mock = new PostBoardRequest();
        mock.setAttributes(attributes);
        return mock;
    }
}