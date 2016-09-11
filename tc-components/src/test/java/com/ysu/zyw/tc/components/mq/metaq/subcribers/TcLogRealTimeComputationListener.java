package com.ysu.zyw.tc.components.mq.metaq.subcribers;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.ysu.zyw.tc.components.mq.metaq.TcMetaMessageListener;
import com.ysu.zyw.tc.components.mq.metaq.model.TcLogMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Formatter;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TcLogRealTimeComputationListener extends TcMetaMessageListener<TcLogMessage> {

    public static final AtomicInteger alreadyReceived = new AtomicInteger(0);

    @Override
    public boolean accept(String group, Message message) {
        return true;
    }

    @Override
    public void doOnReceiveMessages(MetaqMessage<TcLogMessage> msg) {
        System.out.println(new Formatter().format("[id:%s] [topic:%s] [received:%s] [attribute:%s] [msg:%s]",
                msg.getId(),
                msg.getTopic(),
                alreadyReceived.addAndGet(1),
                msg.getAttribute(),
                msg.getBody()));
    }

}
