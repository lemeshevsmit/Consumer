package com.mycompany.app.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.app.config.ProjectProperties;
import com.mycompany.app.factory.ErrorJsonCreator;
import com.mycompany.app.model.ActivePojo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    public void readMessages(ActiveMQConnectionFactory connectionFactory) {
        try {
            // Establish a connection for the consumer.
            // Note: Consumers should not use PooledConnectionFactory.
            final Connection consumerConnection = connectionFactory.createConnection();
            consumerConnection.start();

            // Create a session.
            final Session consumerSession = consumerConnection
                    .createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a queue named "MyQueue".
            final Destination consumerDestination = consumerSession
                    .createQueue(ProjectProperties.getProperties().nameOfQueue);

            // Create a message consumer from the session to the queue.
            final MessageConsumer consumer = consumerSession
                    .createConsumer(consumerDestination);

            String message;
            Locale.setDefault(Locale.ENGLISH);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            File validFile = new File("valid.csv");
            File notValidFile = new File("not-valid.csv");
            try (BufferedWriter writerValid = new BufferedWriter(new FileWriter(validFile, true));
                 BufferedWriter writerNotValid = new BufferedWriter(new FileWriter(notValidFile, true))) {
                writerValid.write(String.join(",", "name", "count\n"));
                writerNotValid.write(String.join(",", "name", "count", "date", "errors\n"));
                do {
                    // Begin to wait for messages.
                    final Message consumerMessage = consumer.receive(1000);

                    // Receive the message when it arrives.
                    final TextMessage consumerTextMessage = (TextMessage) consumerMessage;
                    message = consumerTextMessage.getText();
                    LOGGER.info("READ: {}", message);
                    if (ProjectProperties.getProperties().poisonMessage.equals(message)) {
                        break;
                    } else {
                        ActivePojo pojo = objectMapper.readValue(message, ActivePojo.class);
                        String errors = isValid(pojo);
                        if (errors == null) {
                            writerValid.write(Stream
                                    .of(pojo.getName(), pojo.getCount().toString())
                                    .collect(Collectors.joining(",", "", "\n")));
                        } else {
                            writerNotValid.write(Stream
                                    .of(pojo.getName(), pojo.getCount().toString(), pojo.getCreatedAt().toString(), errors)
                                    .collect(Collectors.joining(",", "", "\n")));
                        }
                    }
                } while (true);
            }
            // Clean up the consumer.
            consumer.close();
            consumerSession.close();
            consumerConnection.close();
        } catch (JMSException e) {
            LOGGER.error("Read message fatal error: {}", e.getMessage());
        } catch (JsonMappingException e) {
            LOGGER.error("Json mapper message fatal error: {}", e.getMessage());
        } catch (JsonProcessingException e) {
            LOGGER.error("Json processing message fatal error: {}", e.getMessage());
        } catch (FileNotFoundException e) {
            LOGGER.error("Json create file fatal error: {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Json write file fatal error: {}", e.getMessage());
        }
    }

    String isValid(ActivePojo pojo) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ActivePojo>> errors = validator.validate(pojo);
            return errors.size() > 0
                    ? new ErrorJsonCreator().createJsonWithErrorMessage(errors)
                    : null;
        }
    }
}
