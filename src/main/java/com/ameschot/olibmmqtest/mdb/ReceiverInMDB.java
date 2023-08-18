package com.ameschot.olibmmqtest.mdb;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.ameschot.olibmmqtest.service.ReceiveAmountService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@MessageDriven
public class ReceiverInMDB implements MessageListener {

    @Inject
    ReceiveAmountService receiveAmountService;

    @Override
    public void onMessage(Message message) {
        receiveAmountService.process(message);
    }
}
