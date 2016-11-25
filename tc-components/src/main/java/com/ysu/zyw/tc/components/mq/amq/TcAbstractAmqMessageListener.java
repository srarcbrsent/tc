package com.ysu.zyw.tc.components.mq.amq;

import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Date;

@Slf4j
public abstract class TcAbstractAmqMessageListener<T> implements SessionAwareMessageListener<TextMessage> {

    @Getter
    @Setter
    private String name = "default";

    @Resource
    private MessageConverter messageConverter;

    @Getter
    @Setter
    private boolean rethrowException;

    @Override
    public void onMessage(TextMessage message, Session session) throws JMSException {
        Date now = new Date();
        if (log.isInfoEnabled()) {
            log.info("amq message listener [{}] receive message", this.getName());
        }
        try {
            @SuppressWarnings("unchecked")
            T o = (T) messageConverter.fromMessage(message);
            this.doOnReceiveMessages(o, message, session);
        } catch (Exception e) {
            log.error("amq message listener [{}] process message [{}] failed", this.getName(), message, e);
            if (rethrowException) {
                JMSException jmsException = new JMSException("");
                jmsException.setLinkedException(e);
                throw jmsException;
            }
        } finally {
            if (log.isInfoEnabled()) {
                log.info("amq message listener [{}] process message [{}] take time [{}ms]",
                        this.getName(), message, TcDateUtils.duration(now, new Date()));
            }
        }
    }

    public abstract <T> void doOnReceiveMessages(T body, TextMessage message, Session session);

}
