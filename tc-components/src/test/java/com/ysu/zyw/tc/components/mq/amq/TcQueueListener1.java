package com.ysu.zyw.tc.components.mq.amq;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Session;
import javax.jms.TextMessage;

@Slf4j
public class TcQueueListener1 extends TcAbstractAmqMessageListener<AmqTest.AmqMsg> {

    @Override
    public <T> void doOnReceiveMessages(T body, TextMessage message, Session session) {
        System.out.println(body);
    }

}
