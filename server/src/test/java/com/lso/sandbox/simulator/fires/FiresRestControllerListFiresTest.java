package com.lso.sandbox.simulator.fires;

import com.lso.sandbox.simulator.fires.add.FireIgnitionUseCase;
import com.lso.sandbox.simulator.fires.list.FireRetrievalUseCase;
import com.lso.sandbox.simulator.fires.list.OngoingFire;
import com.lso.sandbox.simulator.fires.list.OngoingFires;
import com.lso.sandbox.simulator.fires.propagation.FirePropagationUseCase;
import com.lso.sandbox.simulator.repositories.facades.fire.query.OngoingFiresFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de {@link FiresRestController#listFires()}
 */
@WebMvcTest(controllers = FiresRestController.class)
class FiresRestControllerListFiresTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FireIgnitionUseCase ignitionUseCase;

    @MockitoBean
    private FirePropagationUseCase propagationUseCase;

    @Autowired
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
                .andExpect(jsonPath("$.totalElements", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.messages", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[0].x", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.content[0].y", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.content[0].step", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.content[0].alive", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.content[0].dead", Matchers.equalTo(false)))
                .andExpect(jsonPath("$.content[0].burning", Matchers.equalTo(true)))
                .andReturn();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public FireRetrievalUseCase retrieveUseCase() {

            return (input, output) -> {
                OngoingFire item = new OngoingFireMock(1, 2, 3);
                OngoingFires data = OngoingFiresFactory.of(1, List.of(item));

                output.accept(data);
            };
        }
    }

    static class OngoingFireMock implements OngoingFire {
        private final int step;

        private final int x;

        private final int y;

        public OngoingFireMock(int step, int x, int y) {
            this.step = step;
            this.x = x;
            this.y = y;
        }

        @Override
        public int step() {
            return step;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }
}