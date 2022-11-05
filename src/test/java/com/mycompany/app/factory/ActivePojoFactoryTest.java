package com.mycompany.app.factory;

import com.mycompany.app.model.ActivePojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Optional;

class ActivePojoFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivePojoFactoryTest.class);

    @BeforeAll
    static void beforeAll() {
        LOGGER.info("Start testing pojo factory");
    }

    @Test
    void create(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        ActivePojoFactory activePojoFactory = new ActivePojoFactory();
        var result = Optional.ofNullable(activePojoFactory.create());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(ActivePojo.class, activePojoFactory.create().getClass());
        LOGGER.info("Status OK");
    }

    @Test
    void createJson(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        ActivePojo activePojo = new ActivePojo();
        activePojo.setCreatedAt(LocalDate.now());
        activePojo.setCount(10L);
        activePojo.setName("TEST_MESSAGE");

        String expected = "{\"name\":\"TEST_MESSAGE\",\"count\":10,\"createdAt\":[2022,11,5]}";
        String result = new ActivePojoFactory().createJson(activePojo);
        Assertions.assertEquals(expected, result);
        LOGGER.info("Status OK");
    }
}