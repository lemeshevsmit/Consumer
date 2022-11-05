package com.mycompany.app;

import com.mycompany.app.broker.Broker;
import com.mycompany.app.broker.Consumer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;



public class App {
    private static final ActiveMQConnectionFactory connectionFactory =
            Broker.createActiveMQConnectionFactory();
    private static final PooledConnectionFactory pooledConnectionFactory =
            Broker.createPooledConnectionFactory(connectionFactory);

    public static void main(String[] args) {
        new Consumer().readMessages(connectionFactory);

        //pooledConnectionFactory.stop();
    }
}
