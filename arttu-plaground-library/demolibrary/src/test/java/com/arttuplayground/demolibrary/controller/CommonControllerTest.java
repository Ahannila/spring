package com.arttuplayground.demolibrary.controller;

import com.arttuplayground.demolibrary.SpringServiceBean;
import com.arttuplayground.demolibrary.config.TestConfig;
import com.arttuplayground.demolibrary.util.PostgresBinderConfiguration;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.bind.BinderConfiguration;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.arttuplayground.demolibrary.util.DBOperations.CLEAN_DB;
import static com.arttuplayground.demolibrary.util.DBOperations.CREATE_SCHEMA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 31.5.2023
 * This test class show how to test API targeting a real database. Is took in account that Boot is missing,
 * so all the Dependency Injection has to be handled separately (@Bean creation and @Autowired works after
 * specifying the package to scan).
 * As said, the test class works with a real database (specified in application-test.yml), while the library
 * implementation rely on the database specified by the library user.
 */

@EnableWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {TestConfig.class, CommonController.class})
@TestPropertySource(properties = {"common.api.endpoint=test-spring-custom"})
public class CommonControllerTest extends SpringServiceBean {
    private static boolean oneTimeSetupExecuted = false;
    private final String CUSTOM_ENDPOINT = "/api/test-spring-custom";
    private final BinderConfiguration binderConfiguration = new PostgresBinderConfiguration();

    @BeforeEach
    public void cleanDb() {
        if (!oneTimeSetupExecuted) {
            new DbSetup(new DataSourceDestination(dataSource), CREATE_SCHEMA, binderConfiguration).launch();
            oneTimeSetupExecuted = true;
        }

        new DbSetup(new DataSourceDestination(dataSource), CLEAN_DB, binderConfiguration).launch();
    }

    @Test
    public void whenGetEndpointCalledReturn202() throws Exception {

        mockMvc.perform(get(CUSTOM_ENDPOINT + "/get-all"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.length()").value(1));
    }
}
