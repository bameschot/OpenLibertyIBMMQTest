package com.ameschot.olibmmqtest.receivers;

import com.ameschot.olibmmqtest.common.MessageMarshaller;
import com.ameschot.olibmmqtest.common.MessageUnMarshaller;
import com.ameschot.olibmmqtest.common.QueueMessageSender;
import com.ameschot.olibmmqtest.generated.model.TestInMessage;
import com.ameschot.olibmmqtest.generated.model.TestOutMessage;
import com.ameschot.olibmmqtest.service.AccountService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.TextMessage;

@Singleton
@Slf4j
public class ReceiverIn {

    @Resource(lookup = "jms/queueCF")
    ConnectionFactory factory;

    @Resource(lookup = "jms/test/out/queue")
    Queue queueOut;

    @Inject
    QueueMessageSender queueMessageSender;

    @Inject
    AccountService accountService;

    private MessageMarshaller<TestOutMessage> tomMessageMarshaller = new MessageMarshaller<>(TestOutMessage.class);

    private MessageUnMarshaller<TestInMessage> timMessageUnMarshaller = new MessageUnMarshaller<>(TestInMessage.class);

    @SneakyThrows
    public void process(Message message) {

        try {

            log.warn("MDB Received: {}", message);
            String outPayload = tomMessageMarshaller.marshall(doWork(timMessageUnMarshaller.unmarshall(((TextMessage) message).getText())));
            log.warn("MDB Send: {}", outPayload);
            queueMessageSender.send(outPayload, queueOut, factory);
        } catch (Exception e) {
            log.error("Exception processing MDB in for " + queueOut, e);
        }
    }

    TestOutMessage doWork(TestInMessage inMessage) {
        TestOutMessage testOutMessage = new TestOutMessage();
        testOutMessage.setAccount(inMessage.getAccount());
        testOutMessage.setTotal(accountService.getAccounts().compute(inMessage.getAccount(), (account, sum) -> sum == null ? inMessage.getAmount() : sum + inMessage.getAmount()));

        return testOutMessage;
    }
}
