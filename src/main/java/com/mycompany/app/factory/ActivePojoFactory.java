package com.mycompany.app.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.model.ActivePojo;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Random;


public class ActivePojoFactory implements Factory<ActivePojo> {

    private static final Random random = new Random();

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivePojoFactory.class);

    @Override
    public ActivePojo create() {
        ActivePojo activePojo = new ActivePojo();

        Long generatedLong = random
                .longs(0L, 999L)
                .findAny()
                .getAsLong();

        int length = random.nextInt(15);
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString =
                RandomStringUtils.random(length, useLetters, useNumbers);

        LocalDate start = LocalDate.of(1970, Month.JANUARY, 1);
        long days = ChronoUnit.DAYS.between(start, LocalDate.now());
        LocalDate generatedDate =
                start.plusDays(random.nextInt((int) days + 1));

        activePojo.setCount(generatedLong);
        activePojo.setName(generatedString);
        activePojo.setCreatedAt(generatedDate);

        return activePojo;
    }


    public String createJson(ActivePojo pojo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //add LocalDate to module of json mapper
            objectMapper.findAndRegisterModules();
            return objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            LOGGER.error("Create json fatal error: {}", e.getMessage());
            return "FATAL ERROR";
        }
    }
}
