package com.lso.sandbox.simulator.fires;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.repositories.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.CellJpaEntity;
import com.lso.sandbox.simulator.repositories.CellJpaQueryRepository;
import com.lso.sandbox.simulator.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
class FiresRestControllerPostFiresFailsWhenBoardIsNotDefinedTest {

    private static final ObjectMapper mapper = new ObjectMapper();

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

    @MockitoBean
    private CellJpaQueryRepository cellJpaQueryRepository;

    @BeforeEach
    void setUp() {
        Mockito.when(boardJpaCrudRepository.isEmpty()).thenReturn(true);
    }

    @Test
    void should_return_http_code_400() throws Exception {

        // given
        int inputX = RandomUtils.randomInt(0, 10);
        int inputY = RandomUtils.randomInt(0, 10);

        FiresAddRequest requestBody = new FiresAddRequest();
        requestBody.setTargets(List.of(mockItem(inputX, inputY)));
        String requestBodyAsJson = mapper.writeValueAsString(requestBody);

        // when
        ResultActions result = this.mockMvc.perform(post("/api/fires")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(requestBodyAsJson)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private static FiresAddRequest.TargetItem mockItem(int x, int y) {
        FiresAddRequest.TargetItem mock = new FiresAddRequest.TargetItem();
        mock.setX(x);
        mock.setY(y);
        return mock;
    }

    private static CellJpaEntity mockCellEntity(int x, int y) {
        return new CellJpaEntity((byte) x, (byte) y);
    }
}