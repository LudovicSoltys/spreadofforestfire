package com.lso.sandbox.simulator.exposed.board.http.rest;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.BoardRestController;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.AvailableBoard;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
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
@WebMvcTest(controllers = {
        BoardRestController.class,
        BoardRestControllerGetEmptyBoardTest.TestConfig.class
})
class BoardRestControllerGetEmptyBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
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
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.present", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.messages", Matchers.hasSize(0)))
                .andReturn();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        BoardRetrievalUseCase retrieveUseCase() {
            return (input, output) -> {
                AvailableBoard data = AvailableBoard.empty();
                output.accept(data);
            };
        }
    }
}