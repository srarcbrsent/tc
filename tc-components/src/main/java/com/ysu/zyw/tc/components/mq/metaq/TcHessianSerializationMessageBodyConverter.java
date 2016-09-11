package com.ysu.zyw.tc.components.mq.metaq;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.google.common.base.Throwables;
import com.taobao.metamorphosis.client.extension.spring.MessageBodyConverter;
import com.taobao.metamorphosis.exception.MetaClientException;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class TcHessianSerializationMessageBodyConverter<T> implements MessageBodyConverter<T> {

    @Getter
    @Setter
    private int defaultByteArrayBufferSize = 1024;

    @Override
    public byte[] toByteArray(T t) throws MetaClientException {
        checkNotNull(t);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream(defaultByteArrayBufferSize)) {
            Hessian2Output ho = new Hessian2Output(os);
            ho.writeObject(t);
            ho.flush();
            return os.toByteArray();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public T fromByteArray(byte[] bytes) throws MetaClientException {
        checkNotNull(bytes);
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes)) {
            Hessian2Input hi = new Hessian2Input(is);
            //noinspection unchecked
            return (T) hi.readObject();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

}
