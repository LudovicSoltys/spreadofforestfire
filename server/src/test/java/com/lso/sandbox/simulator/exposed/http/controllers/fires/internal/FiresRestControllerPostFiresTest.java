package com.lso.sandbox.simulator.exposed.http.controllers.fires.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.api.FiresRestEndpoint.FiresAddRequest;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireIgnitionUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FirePropagationUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.api.FireRetrievalUseCase;
import com.lso.sandbox.simulator.exposed.http.controllers.fires.internal.domain.spi.CellChanges;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FiresRestController.class)
class FiresRestControllerPostFiresTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private FireIgnitionUseCase ignitionUseCase;

    @MockitoBean
    private FirePropagationUseCase propagationUseCase;

    @MockitoBean
    private FireRetrievalUseCase retrieveUseCase;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void should_apply_changes() throws Exception {

        // given
        FiresAddRequest requestBody = new FiresAddRequest() {

            @Override
            public List<CellChanges> getTargets() {
                return List.of();
            }
        };
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/fires")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andReturn();
    }
}