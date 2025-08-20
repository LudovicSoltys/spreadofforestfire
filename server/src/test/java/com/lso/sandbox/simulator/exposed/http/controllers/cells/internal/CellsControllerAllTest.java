package com.lso.sandbox.simulator.exposed.http.controllers.cells.internal;

import com.lso.sandbox.simulator.exposed.http.controllers.cells.internal.domain.api.CellsListUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CellsController.class)
class CellsControllerAllTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CellsListUseCase listUseCase;

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