package com.lso.sandbox.simulator.cells;

import com.lso.sandbox.simulator.SimulationApplication;
import com.lso.sandbox.simulator.cells.list.CellsListUseCase;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SimulationApplication.class)
class CellsControllerAllE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CellsListUseCase listUseCase;

    @Test
    void context_loads() {

        // then
        Assertions.assertThat(listUseCase).isNotNull();
    }

    @Test
    void should_return_some_content() throws Exception {

        // given


        // when
        ResultActions result = this.mockMvc.perform(get("/api/cells"));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.items", Matchers.hasSize(0)))
                .andReturn();
    }
}