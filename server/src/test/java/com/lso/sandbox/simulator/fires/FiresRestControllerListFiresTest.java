package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
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

@WebMvcTest(controllers = FiresRestController.class)
class FiresRestControllerListFiresTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireIgnitionUseCase ignitionUseCase;

    @MockitoBean
    private FirePropagationUseCase propagationUseCase;

    @MockitoBean
    private FireRetrievalUseCase retrieveUseCase;

    @Test
    void should_return_some_content() throws Exception {

        // given


        // when
        ResultActions result = this.mockMvc.perform(get("/api/fires")).andDo(print());

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(4)))
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.content", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.messages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(0)))
                .andReturn();
    }
}