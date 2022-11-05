package com.mycompany.app.broker;

import com.mycompany.app.config.ProjectProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;


public class Broker {

    private Broker() {
    }

    public static PooledConnectionFactory createPooledConnectionFactory(
            ActiveMQConnectionFactory connectionFactory) {
        // Create a pooled connection factory.
        final PooledConnectionFactory pooledConnectionFactory =
                new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(10);
        return pooledConnectionFactory;
    }

    public static ActiveMQConnectionFactory createActiveMQConnectionFactory() {
        // Create a connection factory.
        final ActiveMQConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(ProjectProperties.getProperties().connectionLink);
        // Pass the username and password.
        connectionFactory.setUserName(ProjectProperties.getProperties().username);
        connectionFactory.setPassword(ProjectProperties.getProperties().password);
        return connectionFactory;
    }
}