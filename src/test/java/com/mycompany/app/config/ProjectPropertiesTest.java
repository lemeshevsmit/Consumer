package com.mycompany.app.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


class ProjectPropertiesTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectPropertiesTest.class);

    @BeforeAll
    static void beforeAll() {
        LOGGER.info("Start testing pojo factory");
    }


    @Test
    void constructor(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        ProjectProperties properties = ProjectProperties.getProperties();
        Assertions.assertEquals("MyQueue", properties.nameOfQueue);
        Assertions.assertEquals("3", properties.runTime);
        Assertions.assertEquals("ssl://b-3dc6b128-c769-42d3-ac48-1e5f61873ae5-1.mq.eu-central-1.amazonaws.com:61617",
                properties.connectionLink);
        Assertions.assertEquals("Avada_Kedavra", properties.poisonMessage);
        Assertions.assertEquals("olexandrsmit", properties.username);
        Assertions.assertEquals("8h5CHafSR4esUak", properties.password);
        LOGGER.info("Status OK");
    }

}