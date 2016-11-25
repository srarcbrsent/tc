package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.taobao.metamorphosis.consumer.ConsumerMessageFilter;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public abstract class TcMetaMessageListener<T> extends DefaultMessageListener<T>
        implements ConsumerMessageFilter {

    @Getter
    @Setter
    private String name = "default";

    @Getter
    @Setter
    private boolean rethrowException;

    @Override
    public void onReceiveMessages(final MetaqMessage<T> msg) {
        Date now = new Date();
        if (log.isInfoEnabled()) {
            log.info("meta message listener [{}] receive message [{}]", this.getName(), msg);
        }
        try {
            this.doOnReceiveMessages(msg);
        } catch (Exception e) {
            log.error("meta message listener [{}] process message [{}] failed", this.getName(), msg, e);
            if (rethrowException) {
                throw new RuntimeException(e);
            }
        } finally {
            if (log.isInfoEnabled()) {
                log.info("meta message listener [{}] process message [{}] take time [{}ms]",
                        this.getName(), msg, TcDateUtils.duration(now, new Date()));
            }
        }
    }

    public abstract void doOnReceiveMessages(final MetaqMessage<T> msg);

}
