package com.lso.sandbox.simulator.board;

import com.lso.sandbox.simulator.SimulationApplication;
import com.lso.sandbox.simulator.board.supplier.BoardRetrievalUseCase;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SimulationApplication.class)
class BoardRestControllerGetPresentBoardE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRetrievalUseCase retrieveUseCase;

    @Test
    void context_loads() {

        // then
        Assertions.assertThat(retrieveUseCase).isNotNull();
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
                .andExpect(jsonPath("$.present", Matchers.equalTo(true)))
                .andExpect(jsonPath("$.width", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.height", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.currentStep", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.green", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.burning", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.dead", Matchers.equalTo(false)))
                .andReturn();
    }
}