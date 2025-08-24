package com.lso.sandbox.simulator.fires;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.BoardJpaEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de {@link FiresRestController#propagateFire(FiresPropagationRequest)}
 */
@WebMvcTest(controllers = FiresRestController.class)
class FiresRestControllerPropagateFireTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final BoardJpaEntity boardEntity = new BoardJpaEntity((byte) 10, (byte) 10);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireIgnitionUseCase ignitionUseCase;

    @MockitoBean
    private FirePropagationUseCase propagationUseCase;

    @MockitoBean
    private FireRetrievalUseCase retrieveUseCase;

    @MockitoBean
    private BoardJpaCrudRepository boardJpaCrudRepository;

    @Test
    void should_apply_changes_when_board_is_defined() throws Exception {

        // given
        Mockito.when(boardJpaCrudRepository.isEmpty()).thenReturn(false);
        Mockito.when(boardJpaCrudRepository.first()).thenReturn(boardEntity);

        FiresPropagationRequest requestBody = new FiresPropagationRequest();
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/fires/next")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void should_return_http_code_400_when_board_is_not_defined() throws Exception {

        // given
        Mockito.when(boardJpaCrudRepository.isEmpty()).thenReturn(true);

        FiresPropagationRequest requestBody = new FiresPropagationRequest();
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/fires/next")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}