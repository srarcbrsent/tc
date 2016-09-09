package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.gecko.core.util.StringUtils;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.client.extension.spring.DefaultMessageListener;
import com.taobao.metamorphosis.client.extension.spring.MessageListenerContainer;
import com.taobao.metamorphosis.client.extension.spring.MetaqTopic;
import com.taobao.metamorphosis.consumer.ConsumerMessageFilter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class TcMetaMessageQueueListenerRegistry extends MessageListenerContainer {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Start to initialize message listener container.");
        if (this.getSubscribers() != null) {
            Set<MessageConsumer> consumers = new HashSet<>();
            for (Map.Entry<MetaqTopic, ? extends DefaultMessageListener<?>> entry : this.getSubscribers().entrySet()) {
                final MetaqTopic topic = entry.getKey();
                final DefaultMessageListener<?> listener = entry.getValue();
                if (topic == null) {
                    throw new IllegalArgumentException("Topic is null");
                }
                if (StringUtils.isBlank(topic.getTopic())) {
                    throw new IllegalArgumentException("Blank topic");
                }
                MessageConsumer consumer = this.getMessageConsumer(topic);
                if (consumer == null) {
                    throw new IllegalStateException("Get or create consumer failed");
                }
                log.info("Subscribe topic=" + topic.getTopic() + " with group=" + topic.getGroup());
                if (listener.getMessageBodyConverter() == null) {
                    listener.setMessageBodyConverter(this.getMessageBodyConverter());
                }
                consumer.subscribe(topic.getTopic(), topic.getMaxBufferSize(), listener,
                        this.tryConvertListener2ConsumerFilter(listener));
                consumers.add(consumer);
            }
            for (MessageConsumer consumer : consumers) {
                consumer.completeSubscribe();
            }
        }
        log.info("Initialize message listener container successfully.");
    }

    private ConsumerMessageFilter tryConvertListener2ConsumerFilter(MessageListener messageListener) {
        if (Objects.nonNull(messageListener) && messageListener instanceof ConsumerMessageFilter) {
            return (ConsumerMessageFilter) messageListener;
        }
        return null;
    }

}
