package org.beyond_infinity.app.cucumber.stepdefs;

import org.beyond_infinity.app.BeyondInfinityApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BeyondInfinityApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
