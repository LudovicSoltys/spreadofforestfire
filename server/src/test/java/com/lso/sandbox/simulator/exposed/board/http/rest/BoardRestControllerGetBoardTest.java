package com.lso.sandbox.simulator.exposed.board.http.rest;

import com.lso.sandbox.simulator.exposed.board.http.rest.internal.BoardRestController;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.registror.api.BoardUpdatingUseCase;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.AvailableBoard;
import com.lso.sandbox.simulator.exposed.board.http.rest.internal.domain.supplier.api.BoardRetrievalUseCase;
import com.lso.sandbox.simulator.utils.RandomUtils;
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
        BoardRestControllerGetBoardTest.TestConfig.class
})
class BoardRestControllerGetBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRetrievalUseCase retrieveUseCase;

    @MockitoBean
    private BoardUpdatingUseCase updateUseCase;

    @Test
    void should_return_some_valid_content_with_http_code_200() throws Exception {

        // given

        // when
        ResultActions result = this.mockMvc.perform(get("/api/board")).andDo(print());

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.present", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.width", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.height", Matchers.equalTo(4)))
                .andExpect(jsonPath("$.currentStep", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.messages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(0)))
                .andReturn();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        BoardRetrievalUseCase retrieveUseCase() {
            return (input, output) -> {
                AvailableBoard data = new AvailableBoard() {
                    @Override
                    public long getWidth() {
                        return 3;
                    }

                    @Override
                    public long getHeight() {
                        return 4;
                    }

                    @Override
                    public int currentStep() {
                        return 5;
                    }

                    @Override
                    public int burningCount() {
                        return RandomUtils.randomInt(0, 100);
                    }

                    @Override
                    public int deadCount() {
                        return RandomUtils.randomInt(0, 100);
                    }
                };
                output.accept(data);
            };
        }
    }
}