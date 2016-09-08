package com.ysu.zyw.tc.components.mq.meta;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.taobao.metamorphosis.consumer.ConsumerMessageFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;

@Slf4j
public abstract class TcMetaMessageQueueListener<T>
        extends DefaultMessageListener<T>
        implements ConsumerMessageFilter, BeanNameAware {

    @Getter
    @Setter
    private String name = "default";

    @Override
    public abstract boolean accept(final String group, final Message message);

    @Override
    public void onReceiveMessages(final MetaqMessage<T> msg) {
        if (log.isDebugEnabled()) {
            log.debug("receive msg with id [{}], topic [{}], attribute [{}], partition [{}], msg [{}]",
                    msg.getId(),
                    msg.getTopic(),
                    msg.getAttribute(),
                    msg.getPartition(),
                    msg);
        }

        long startTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("thread [{}] start process message, id [{}]",
                    Thread.currentThread(),
                    msg.getTopic(),
                    msg.getId());
        }

        try {
            this.onReceiveMessage(msg);
        } catch (Exception e) {
            log.error("catch exception when process msg [{}]", msg, e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("thread [{}] finish process message, id [{}], take time [{}]",
                        Thread.currentThread(),
                        msg.getTopic(),
                        msg.getId(),
                        System.currentTimeMillis() - startTime);
            }
        }
    }

    public abstract void onReceiveMessage(final MetaqMessage<T> msg);

}
