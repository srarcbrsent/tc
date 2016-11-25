package com.ysu.zyw.tc.components.mq.amq;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class TcQueueListener1 extends TcAbstractAmqMessageListener<AmqTest.AmqMsg> {

    private AtomicLong consumed = new AtomicLong(0);

    @Override
    public <T> void doOnReceiveMessages(T body, TextMessage message, Session session) {
        System.out.println(consumed.getAndAdd(1) + " " + body);
    }

}
