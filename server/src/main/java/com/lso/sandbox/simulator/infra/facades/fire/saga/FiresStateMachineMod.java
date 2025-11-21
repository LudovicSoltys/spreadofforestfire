package com.lso.sandbox.simulator.infra.facades.fire.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * Module de création de nouveaux incendies
 */
@Configuration
@EnableStateMachine
public class FiresStateMachineMod extends StateMachineConfigurerAdapter<String, String> {

    private static final Logger LOG = LoggerFactory.getLogger(FiresStateMachineMod.class);

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states)
            throws Exception {

        states
                .withStates()
                    .initial("SI")
                    .fork("SFork")
                    .join("SJoin")
                    .end("SF")
                    .and()
                .withStates()
                    .parent("SFork")
                    .initial("Sub1-1", entryAction())
                    .state("S4", executeAction(), errorAction())
                    .end("Sub1-2")
                    .and()
                .withStates()
                    .parent("SFork")
                    .initial("Sub2-1", entryAction())
                    .state("S5", executeAction(), errorAction())
                    .end("Sub2-2");
    }

    @Override
    public void configure(
            StateMachineTransitionConfigurer<String, String> transitions)
            throws Exception {

        transitions
                .withExternal()
                    .source("SI").target("SFork").event("E1")
                    .and()
                .withExternal()
                    .source("Sub1-1").target("Sub1-2").event("sub1").guard(simpleGuard())
                    .and()
                .withExternal()
                    .source("Sub2-1").target("Sub2-2").event("sub2").guard(simpleGuard())
                    .and()
                .withFork()
                    .source("SFork")
                    .target("Sub1-1")
                    .target("Sub2-1")
                    .and()
                .withJoin()
                    .source("Sub1-2")
                    .source("Sub2-2")
                    .target("SJoin");
    }

    @Bean
    public Action<String, String> entryAction() {
        return ctx -> System.out.println(
                "Entry " + ctx.getTarget().getId());
    }

    @Bean
    public Action<String, String> executeAction() {
        return ctx ->
                System.out.println("Do " + ctx.getTarget().getId());
    }

    @Bean
    public Action<String, String> exitAction() {
        return ctx -> System.out.println(
                "Exit " + ctx.getSource().getId() + " -> " + ctx.getTarget().getId());
    }

    @Bean
    public Guard<String, String> simpleGuard() {
        return ctx -> (int) ctx.getExtendedState()
                .getVariables()
                .getOrDefault("approvalCount", 0) > 0;
    }

    @Bean
    public Action<String, String> errorAction() {
        return ctx -> LOG.info("Error " + ctx
                .getSource()
                .getId() + ctx.getException());
    }

    static class StateMachineListener extends StateMachineListenerAdapter<String, String> {

        private static final Logger LOGGER = LoggerFactory.getLogger(StateMachineListener.class);

        @Override
        public void stateChanged(State from, State to) {
            LOGGER.info("Transitioned from {} to {}.", from == null ? "none" : from.getId(), to.getId());
        }
    }
}
