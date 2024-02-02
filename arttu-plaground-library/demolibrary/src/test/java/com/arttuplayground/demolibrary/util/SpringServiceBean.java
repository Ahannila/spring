package com.arttuplayground.demolibrary.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

/**
 * This class provide common services to use in all the test classes.
 */

@TestPropertySource(properties = {
        "logging.level.org.springframework.test.jdbc.JdbcTestUtils=OFF"
})

public abstract class SpringServiceBean {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected Mapper mapper;
    @Autowired
    protected ObjectMapper objectMapper;

}
