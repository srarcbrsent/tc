package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MetaqMessage;
import com.taobao.metamorphosis.consumer.ConsumerMessageFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
public abstract class TcMetaMessageListener<T> extends DefaultMessageListener<T>
        implements ConsumerMessageFilter {

    @Getter
    @Setter
    private String name = "default";

    @Override
    public void onReceiveMessages(final MetaqMessage<T> msg) {
        LocalDateTime now = LocalDateTime.now();
        if (log.isInfoEnabled()) {
            log.info("meta message listener [{}] receive message [{}]", this.getName(), msg);
        }
        try {
            this.doOnReceiveMessages(msg);
        } catch (Exception e) {
            log.error("meta message listener [{}] process message [{}] failed", this.getName(), msg, e);
        } finally {
            if (log.isInfoEnabled()) {
                log.info("meta message listener [{}] process message [{}] take time [{}ns]",
                        this.getName(), msg, Duration.between(now, LocalDateTime.now()).get(ChronoUnit.NANOS));
            }
        }
    }

    public abstract void doOnReceiveMessages(final MetaqMessage<T> msg);

}
