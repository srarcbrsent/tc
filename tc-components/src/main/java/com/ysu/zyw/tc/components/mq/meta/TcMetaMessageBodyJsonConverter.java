package com.ysu.zyw.tc.components.mq.meta;

import com.taobao.metamorphosis.client.extension.spring.MessageBodyConverter;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.ysu.zyw.tc.base.utils.JsonUtil;

import java.nio.charset.Charset;

public class TcMetaMessageBodyJsonConverter implements MessageBodyConverter<Object> {

    @Override
    public byte[] toByteArray(Object body) throws MetaClientException {
        return JsonUtil.serialize(body).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public Object fromByteArray(byte[] bs) throws MetaClientException {
        String s = new String(bs, Charset.forName("UTF-8"));
        return JsonUtil.deserialize(s, Object.class);
    }

}
