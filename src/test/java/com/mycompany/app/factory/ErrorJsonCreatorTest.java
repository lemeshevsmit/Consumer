package com.mycompany.app.factory;

import com.mycompany.app.model.ActivePojo;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

class ErrorJsonCreatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorJsonCreatorTest.class);

    @BeforeAll
    static void beforeAll() {
        LOGGER.info("Start testing error message creator");
    }

    @Test
    void createJsonWithErrorMessage(TestInfo testInfo) {
        LOGGER.info("Testing: {}", testInfo.getTestMethod().get().getName());
        Locale.setDefault(Locale.ENGLISH);
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var errors =
                    validator.validateValue(ActivePojo.class, "name", "Viv");
            var expected = "{\"errors\":[" +
                    "\"Bad formed name: Viv must contains: a\"," +
                    "\"name length must be greater than or equal to 7\"]}";

            var result = new ErrorJsonCreator().createJsonWithErrorMessage(errors);
            Assertions.assertEquals(expected, result);
        }
        LOGGER.info("Status OK");
    }
}