package com.ysu.zyw.tc.components.httpx;

import com.google.common.base.Preconditions;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ClassUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TcHttpxService {

    @Getter
    @Setter
    @Resource
    private RestTemplate restTemplate;

    public void getText4Entity(@Nonnull String uri,
                               @Nullable Map<String, String> pathVariables,
                               @Nullable HttpHeaders httpHeaders,
                               @Nonnull MultiValueMap<String, String> requestBody,
                               @Nonnull ParameterizedTypeReference typeReference) {
    }

    public void postText4Entity(@Nonnull String uri,
                                @Nullable Map<String, String> pathVariables,
                                @Nullable HttpHeaders httpHeaders,
                                @Nonnull MultiValueMap<String, String> requestBody,
                                @Nonnull ParameterizedTypeReference typeReference) {

    }

    public void postJson4Entity(@Nonnull String uri,
                                @Nullable Map<String, String> pathVariables,
                                @Nullable HttpHeaders httpHeaders,
                                @Nonnull Object requestBody,
                                @Nonnull ParameterizedTypeReference typeReference) {

    }

    public MultiValueMap<String, String> wrapObj2MultiValueMap(@Nullable Object obj) {
        if (Objects.isNull(obj)) {
            return new LinkedMultiValueMap<>();
        }
        if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
            throw new TcException("primitive or wrapper value is invalid");
        }


        return null;
    }

    private void wrapObj2MultiValueMap(MultiValueMap<String, String> multiValueMap, String currentPath, Object obj) {
        if (ClassUtils.isPrimitiveOrWrapper(obj.getClass())) {
            // is primitive type

        } else if (obj instanceof Map) {
            // is map
        } else if (obj instanceof Collection) {
            // is collection

        } else if (obj.getClass().isArray()) {
            // is array
            for (int i = 0; i < Array.getLength(obj); i++) {

            }
        } else {
            // is pojo
        }
    }

    private String expandVars(MultiValueMap<String, String> form) {
        Preconditions.checkNotNull(form);
        StringBuilder builder = new StringBuilder();
        for (Iterator<String> nameIterator = form.keySet().iterator(); nameIterator.hasNext(); ) {
            String name = nameIterator.next();
            for (Iterator<String> valueIterator = form.get(name).iterator(); valueIterator.hasNext(); ) {
                String value = valueIterator.next();
                builder.append(name);
                if (value != null) {
                    builder.append('=');
                    builder.append(value);
                    if (valueIterator.hasNext()) {
                        builder.append('&');
                    }
                }
            }
            if (nameIterator.hasNext()) {
                builder.append('&');
            }
        }
        return builder.toString();
    }

    private void execute() {
//        restTemplate.exchange(url, httpMethod, httpEntity, typeReference, uriVariables);
    }

}
