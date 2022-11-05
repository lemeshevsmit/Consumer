package com.mycompany.app.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;

class ActivePojoTest {

    ActivePojo activePojo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivePojoTest.class);

    private static final String NAME = "Lemesheva Regina";
    private static final Long COUNT = 10L;
    private static final LocalDate DATE = LocalDate.of(2021, Month.JUNE, 8);


    @BeforeAll
    static void beforeAll() {
        LOGGER.info("Start testing pojo model");
    }

    @BeforeEach
    void init() {
        activePojo = new ActivePojo();
        activePojo.setCreatedAt(DATE);
        activePojo.setCount(COUNT);
        activePojo.setName(NAME);
    }

    @AfterEach
    void clear() {
        activePojo = null;
    }

    @Test
    void getName(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        Assertions.assertEquals(NAME, activePojo.getName());
        LOGGER.info("Status OK");
    }

    @Test
    void setName(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        String expected = "test-message";
        activePojo.setName(expected);
        Assertions.assertEquals(expected, activePojo.getName());
        LOGGER.info("Status OK");
    }

    @Test
    void getCount(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        Assertions.assertEquals(COUNT, activePojo.getCount());
        LOGGER.info("Status OK");
    }

    @Test
    void setCount(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        Long expected = 55L;
        activePojo.setCount(expected);
        Assertions.assertEquals(expected, activePojo.getCount());
        LOGGER.info("Status OK");
    }

    @Test
    void getCreatedAt(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        Assertions.assertEquals(DATE, activePojo.getCreatedAt());
        LOGGER.info("Status OK");
    }

    @Test
    void setCreatedAt(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        LocalDate expected = LocalDate.now();
        activePojo.setCreatedAt(expected);
        Assertions.assertEquals(expected, activePojo.getCreatedAt());
        LOGGER.info("Status OK");
    }

    @Test
    void toString(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        String expected = "ActivePojo{name='" + NAME +
                "', count=" + COUNT +
                ", createdAt=" + DATE + "}";
        Assertions.assertEquals(expected, activePojo.toString());
        LOGGER.info("Status OK");
    }
}