package com.mycompany.app.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.model.ActivePojo;
import com.mycompany.app.model.ErrorMessage;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class ErrorJsonCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorJsonCreator.class);

    public String createJsonWithErrorMessage(Set<ConstraintViolation<ActivePojo>> errors) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorMessage json = new ErrorMessage();
            json.setErrors(errors
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList()));
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            LOGGER.error("Create json fatal error: {}", e.getMessage());
            return "FATAL ERROR";
        }
    }
}
