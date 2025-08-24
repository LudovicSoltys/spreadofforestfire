package com.lso.sandbox.simulator.fires;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.repositories.data.BoardJpaCrudRepository;
import com.lso.sandbox.simulator.repositories.data.BoardJpaEntity;
import com.lso.sandbox.simulator.repositories.data.CellJpaEntity;
import com.lso.sandbox.simulator.repositories.data.CellJpaQueryRepository;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de {@link FiresRestController#addFires(FiresAddRequest)}
 */
@WebMvcTest(controllers = FiresRestController.class)
class FiresRestControllerAddFiresTest {

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

    @MockitoBean
    private CellJpaQueryRepository cellJpaQueryRepository;

    @BeforeEach
    void setUp() {

        Mockito.when(boardJpaCrudRepository.isEmpty()).thenReturn(false);
        Mockito.when(boardJpaCrudRepository.first()).thenReturn(boardEntity);

    }

    @Test
    void should_return_http_code_400_when_input_target_list_is_empty() throws Exception {

        // given
        FiresAddRequest requestBody = new FiresAddRequest();
        requestBody.setTargets(List.of());
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

    @Test
    void should_return_http_code_400_when_input_target_item_is_out_of_bound() throws Exception {

        // given
        Mockito.when(cellJpaQueryRepository.findByXAndY(1, 10)).thenReturn(Optional.of(mockCellEntity(1, 10)));

        FiresAddRequest requestBody = new FiresAddRequest();
        requestBody.setTargets(List.of(mockItem(1, 10)));
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

    @Test
    void should_apply_changes_when_input_item_is_valid() throws Exception {

        // given
        int inputX = RandomUtils.randomInt(0, 10);
        int inputY = RandomUtils.randomInt(0, 10);
        Mockito.when(cellJpaQueryRepository.findByXAndY(inputX, inputY)).thenReturn(Optional.of(mockCellEntity(inputX, inputY)));

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
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void should_apply_changes_when_input_items_are_valid() throws Exception {

        // given
        Mockito.when(cellJpaQueryRepository.findByXAndY(0, 0)).thenReturn(Optional.of(mockCellEntity(0, 0)));
        Mockito.when(cellJpaQueryRepository.findByXAndY(9, 9)).thenReturn(Optional.of(mockCellEntity(9, 9)));
        Mockito.when(cellJpaQueryRepository.findByXAndY(1, 5)).thenReturn(Optional.of(mockCellEntity(1, 5)));

        FiresAddRequest requestBody = new FiresAddRequest();
        requestBody.setTargets(List.of(mockItem(0, 0), mockItem(9, 9), mockItem(1, 5)));
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


    @Test
    void should_apply_changes_when_duplicate_input_items_are_valid() throws Exception {

        // given
        Mockito.when(cellJpaQueryRepository.findByXAndY(1, 5)).thenReturn(Optional.of(mockCellEntity(1, 5)));

        FiresAddRequest requestBody = new FiresAddRequest();
        requestBody.setTargets(List.of(mockItem(1, 5), mockItem(1, 5)));
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