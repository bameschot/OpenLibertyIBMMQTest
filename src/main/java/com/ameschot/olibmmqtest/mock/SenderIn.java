package com.ameschot.olibmmqtest.mock;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import com.ameschot.olibmmqtest.common.MessageMarshaller;
import com.ameschot.olibmmqtest.common.QueueMessageSender;
import com.ameschot.olibmmqtest.generated.model.TestInMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class SenderIn {

    @Resource(lookup="jms/queueCF")
    ConnectionFactory factory;

    @Resource(lookup = "jms/test/in/queue")
    Queue queue;

    @Inject
    QueueMessageSender queueMessageSender;

    MessageMarshaller<TestInMessage> testInMessageMessageMarshaller = new MessageMarshaller<>(TestInMessage.class);


    @SneakyThrows
    public void send(TestInMessage message){
        queueMessageSender.send(testInMessageMessageMarshaller.marshall(message),queue,factory);
    }

}