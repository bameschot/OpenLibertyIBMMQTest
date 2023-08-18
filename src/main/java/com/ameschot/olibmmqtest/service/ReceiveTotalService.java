package com.ameschot.olibmmqtest.service;

import javax.annotation.Resource;
import javax.ejb.Singleton;

import com.ameschot.olibmmqtest.common.MessageUnMarshaller;
import com.ameschot.olibmmqtest.generated.model.TestOutMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Slf4j
@Singleton
public class ReceiveTotalService {

    @Resource(lookup = "jms/queueCF")
    ConnectionFactory factory;

    @Resource(lookup = "jms/test/out/queue")
    Queue queue;

    MessageUnMarshaller<TestOutMessage> tomMessageUnMarshaller = new MessageUnMarshaller<>(TestOutMessage.class);

    @SneakyThrows
    public TestOutMessage receive() {

        // Establish a connection for the consumer.
        try (Connection connection = factory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             final MessageConsumer consumer = session.createConsumer(queue)) {
            connection.start();

            //Begin to wait for messages and receive the message when it arrives.
            final Message consumerMessage = consumer.receive(1000);

            if (consumerMessage != null) {
                // Receive the message when it arrives
                final TextMessage consumerTextMessage = (TextMessage) consumerMessage;
                String payload = consumerTextMessage.getText();
                log.debug("Received Message {}", consumerTextMessage);
                return tomMessageUnMarshaller.unmarshall(payload);
            }
            return null;
        }
    }


}
