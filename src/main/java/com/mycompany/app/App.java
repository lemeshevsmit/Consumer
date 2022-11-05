package com.mycompany.app;

import com.mycompany.app.broker.Broker;
import com.mycompany.app.broker.Producer;
import com.mycompany.app.factory.ActivePojoFactory;
import com.mycompany.app.config.ProjectProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class App {
    private static final ActiveMQConnectionFactory connectionFactory =
            Broker.createActiveMQConnectionFactory();
    private static final PooledConnectionFactory pooledConnectionFactory =
            Broker.createPooledConnectionFactory(connectionFactory);

    public static void main(String[] args) {
        String property = System.getProperty("N");
        int count = (property == null) ? 0 : Integer.parseInt(property);

        ActivePojoFactory activePojoFactory = new ActivePojoFactory();
        long runTime = Long.parseLong(ProjectProperties.getProperties().runTime);
        long startTime = LocalTime.now().getLong(ChronoField.SECOND_OF_DAY);

        Producer producer = new Producer();
        producer.sentMessages(pooledConnectionFactory, IntStream.iterate(0,
                        i -> ((LocalTime.now().getLong(ChronoField.SECOND_OF_DAY) - startTime) <= runTime) && (i < count),
                        i -> i + 1)
                .mapToObj(i -> activePojoFactory.create())
                .map(activePojoFactory::createJson)
                .collect(Collectors.toList()));

        pooledConnectionFactory.stop();
    }
}
