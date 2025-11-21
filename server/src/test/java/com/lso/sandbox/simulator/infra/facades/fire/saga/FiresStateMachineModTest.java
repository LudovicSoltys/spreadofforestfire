package com.lso.sandbox.simulator.infra.facades.fire.saga;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.util.Arrays;

@SpringBootTest(classes = {FiresStateMachineMod.class})
class FiresStateMachineModTest {

    @Autowired
    private StateMachine<String, String> stateMachine;

    @BeforeEach
    void setUp() {
        stateMachine.startReactively();
    }

    @AfterEach
    void tearDown() {
        stateMachine.stopReactively();
    }

    @Test
    public void whenForkStateEntered_thenMultipleSubStatesEntered() {
        boolean success = stateMachine.sendEvent("E1");

        Assertions.assertThat(success).isTrue();

        Assertions.assertThat(stateMachine.getState().getIds()).containsAll(Arrays.asList("SFork", "Sub1-1", "Sub2-1"));
    }

    @Test
    public void whenAllConfiguredJoinEntryStatesAreEntered_thenTransitionToJoinState() {

        boolean success = stateMachine.sendEvent("E1");

        Assertions.assertThat(success).isTrue();

        Assertions.assertThat(stateMachine.getState().getIds()).containsAll(Arrays.asList("SFork", "Sub1-1", "Sub2-1"));

        Assertions.assertThat(stateMachine.sendEvent("sub1")).isTrue();
        Assertions.assertThat(stateMachine.sendEvent("sub2")).isTrue();
        Assertions.assertThat(stateMachine.getState().getId()).isEqualTo("SJoin");
    }
}