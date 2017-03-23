package com.ysu.zyw.tc.components.se.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.FactoryBean;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcTransportClientFactoryBean implements FactoryBean<TransportClient>, Closeable {

    @Getter
    @Setter
    private Map<String, Integer> inetAddresses;

    @Getter
    @Setter
    private Map<String, String> settings;

    private TransportClient client;

    @Override
    public TransportClient getObject() throws Exception {
        checkNotNull(settings);
        checkArgument(MapUtils.isNotEmpty(inetAddresses));
        PreBuiltTransportClient client = new PreBuiltTransportClient(Settings.builder().put(settings).build());
        inetAddresses.forEach((key, value) -> {
            try {
                client.addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(key), value));
                log.info("add socket transport address [{}:{}] to client", key, value);
            } catch (UnknownHostException e) {
                throw new IllegalStateException(e);
            }
        });
        this.client = client;
        log.info("successful create transport client instance ...");
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(client)) {
            client.close();
            log.info("successful close transport client ...");
        }
    }
}
