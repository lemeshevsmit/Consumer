package com.mycompany.app.broker;

import com.mycompany.app.config.ProjectProperties;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;

public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public void sentMessages(PooledConnectionFactory pooledConnectionFactory,
                             List<String> messages) {
        try {
            // Establish a connection for the producer.
            final Connection producerConnection = pooledConnectionFactory
                    .createConnection();
            producerConnection.start();

            // Create a session.
            final Session producerSession = producerConnection
                    .createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a queue named "MyQueue".
            final Destination producerDestination = producerSession
                    .createQueue(ProjectProperties.getProperties().nameOfQueue);

            // Create a producer from the session to the queue.
            final MessageProducer producer = producerSession
                    .createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages and send.
            for (String message : messages) {
                TextMessage producerMessage = producerSession
                        .createTextMessage(message);
                LOGGER.info("WRITE: {}", producerMessage.getText());
                producer.send(producerMessage);
            }

            // Add poison pill message
            TextMessage producerMessage = producerSession
                    .createTextMessage(ProjectProperties.getProperties().poisonMessage);
            LOGGER.info("WRITE: {}", producerMessage.getText());
            producer.send(producerMessage);

            // Clean up the producer.
            producer.close();
            producerSession.close();
            producerConnection.close();

        } catch (JMSException e) {
            LOGGER.error("PRODUCER CONNECTION FATAL ERROR: {}", e.getMessage());
        }
    }
}
