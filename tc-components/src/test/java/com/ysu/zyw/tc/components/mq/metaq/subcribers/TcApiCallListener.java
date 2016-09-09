package com.ysu.zyw.tc.components.mq.metaq.subcribers;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.ysu.zyw.tc.components.mq.metaq.TcMetaMessageListener;
import com.ysu.zyw.tc.components.mq.metaq.model.TcApiCallMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Formatter;

@Slf4j
public class TcApiCallListener extends TcMetaMessageListener<TcApiCallMessage> {

    public static int alreadyReceived = 0;

    @Override
    public boolean accept(String group, Message message) {
        return true;
    }

    @Override
    public void doOnReceiveMessages(MetaqMessage<TcApiCallMessage> msg) {
        System.out.println(new Formatter().format("[id:%s] [topic:%s] [received:%s] [attribute:%s] [msg:%s]",
                msg.getId(),
                msg.getTopic(),
                ++alreadyReceived,
                msg.getAttribute(),
                msg.getBody()));
    }

}
