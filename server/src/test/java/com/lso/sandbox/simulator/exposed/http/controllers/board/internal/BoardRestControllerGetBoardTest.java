package com.lso.sandbox.simulator.exposed.http.controllers.board.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.board.internal.domain.api.BoardUpdatingUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de {@link BoardRestController#getBoard()}
 */
@WebMvcTest(controllers = BoardRestController.class)
class BoardRestControllerGetBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardRetrievalUseCase retrieveUseCase;

    @MockitoBean
    private BoardUpdatingUseCase updateUseCase;

    @Test
    void should_return_some_empty_content_with_http_code_200() throws Exception {

        // given


        // when
        ResultActions result = this.mockMvc.perform(get("/api/board")).andDo(print());

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(9)))
                .andExpect(jsonPath("$.present", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.messages", Matchers.hasSize(0)))
                .andReturn();
    }

    @Test
    void should_return_some_valid_content_with_http_code_200() throws Exception {

        // given

        // when
        ResultActions result = this.mockMvc.perform(get("/api/board")).andDo(print());

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(9)))
                .andExpect(jsonPath("$.present", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.width", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.height", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.currentStep", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.green", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.burning", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.dead", Matchers.equalTo(false)))
                .andReturn();
    }
}