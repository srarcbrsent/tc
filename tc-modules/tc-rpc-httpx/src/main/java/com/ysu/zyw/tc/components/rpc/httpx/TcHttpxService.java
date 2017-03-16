package com.ysu.zyw.tc.components.rpc.httpx;

import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.constant.TcStrConsts;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @warn do not directly return the responseEntity to springmvc's controller,
 * because this responseEntity means the response of the next layer, and the
 * springmvc's return value means the response of this layer, and the response
 * of next layer will carry some special response headers(such as encoding,
 * transfer-encoding gzip etc.), and those response header has it's own meaning.
 * <p>
 * for example, the lower layer server encoding the response to gzip, and add a
 * header named content-encoding: gzip, and upper layer receive the response
 * carry a header named content-encoding: gzip, and directly return it to
 * springmvc controller, springmvc(or container, proxy etc) see this header,
 * they do not encoding the response to gzip, but the user do not encoding
 * the response stream to gzip, but set a header content-encoding: gzip, so
 * this will lead to a mistake.
 */
@Slf4j
public class TcHttpxService {

    @Getter
    @Setter
    @Resource
    private RestTemplate restTemplate;

    @Getter
    @Setter
    private boolean swallowException;

    public <T> ResponseEntity<T> getText4Entity(@Nonnull String url,
                                                @Nullable Map<String, String> pathVariables,
                                                @Nullable HttpHeaders httpHeaders,
                                                @Nonnull MultiValueMap<String, String> requestBody,
                                                @Nonnull ParameterizedTypeReference<T> typeReference) {
        checkNotNull(url);
        checkNotNull(requestBody);
        checkNotNull(typeReference);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null,
                addReqContentType(httpHeaders, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        String expandVars = expandVars(requestBody);
        String expandUrl = StringUtils.hasText(expandVars) ? (url + "?" + expandVars) : url;
        return execute(expandUrl, HttpMethod.GET, httpEntity, typeReference, pathVariables);
    }

    public <T> ResponseEntity<T> postText4Entity(@Nonnull String url,
                                                 @Nullable Map<String, String> pathVariables,
                                                 @Nullable HttpHeaders httpHeaders,
                                                 @Nonnull MultiValueMap<String, String> requestBody,
                                                 @Nonnull ParameterizedTypeReference<T> typeReference) {
        checkNotNull(url);
        checkNotNull(requestBody);
        checkNotNull(typeReference);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody,
                addReqContentType(httpHeaders, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        return execute(url, HttpMethod.POST, httpEntity, typeReference, pathVariables);
    }

    public <T> ResponseEntity<T> postJson4Entity(@Nonnull String url,
                                                 @Nullable Map<String, String> pathVariables,
                                                 @Nullable HttpHeaders httpHeaders,
                                                 @Nonnull Object requestBody,
                                                 @Nonnull ParameterizedTypeReference<T> typeReference) {
        checkNotNull(url);
        checkNotNull(requestBody);
        checkNotNull(typeReference);
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody,
                addReqContentType(httpHeaders, MediaType.APPLICATION_JSON_UTF8_VALUE));
        return execute(url, HttpMethod.POST, httpEntity, typeReference, pathVariables);
    }

    public MultiValueMap<String, String> copy2MultiValueMap(@Nullable Object obj) {
        if (Objects.isNull(obj)) {
            return new LinkedMultiValueMap<>();
        }
        if (obj instanceof Collection
                || obj.getClass().isArray()
                || ClassUtils.isPrimitiveOrWrapper(obj.getClass())
                || obj instanceof String) {
            throw new IllegalArgumentException("list / primitive / string value is not supported");
        }

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        copy2MultiValueMap(multiValueMap, TcStrConsts.BLANK, obj);
        return multiValueMap;
    }

    protected void copy2MultiValueMap(MultiValueMap<String, String> multiValueMap,
                                      String currentPath,
                                      Object obj) {
        if (Objects.isNull(obj)) {
            return;
        }
        if (ClassUtils.isPrimitiveOrWrapper(obj.getClass()) || obj instanceof String) {
            // is primitive type: char, boolean, byte, short, int, long, float, double
            multiValueMap.set(currentPath, String.valueOf(obj));
        } else if (obj instanceof Date) {
            // is date
            multiValueMap.set(currentPath, TcDateUtils.format((Date) obj));
        } else if (obj instanceof Map) {
            // is map
            Map<?, ?> rValue = (Map) obj;
            rValue.entrySet().forEach(entry -> {
                checkArgument(entry.getKey() instanceof String, "map key must is string");
                String key = String.valueOf(entry.getKey());
                String oKey = StringUtils.hasText(currentPath)
                        ? (currentPath + TcStrConsts.DOT + key) : key;
                Object oValue = entry.getValue();
                copy2MultiValueMap(multiValueMap, oKey, oValue);
            });
        } else if (obj instanceof Collection) {
            // is collection
            Collection value = (Collection) obj;
            int index = 0;
            for (Object oValue : value) {
                String oKey = currentPath + "[" + index + "]";
                copy2MultiValueMap(multiValueMap, oKey, oValue);
                index++;
            }
        } else if (obj.getClass().isArray()) {
            // is array
            for (int i = 0; i < Array.getLength(obj); i++) {
                String oKey = currentPath + "[" + i + "]";
                copy2MultiValueMap(multiValueMap, oKey, Array.get(obj, i));
            }
        } else {
            // is pojo
            ReflectionUtils.doWithFields(obj.getClass(), field -> {
                try {
                    String key = field.getName();
                    Method readMethod = findPropertyReadMethod(key, field.getDeclaringClass());
                    String oKey = StringUtils.hasText(currentPath)
                            ? (currentPath + TcStrConsts.DOT + key) : key;
                    Object oValue = readMethod.invoke(obj);
                    copy2MultiValueMap(multiValueMap, oKey, oValue);
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    protected Method findPropertyReadMethod(String propertyName, Class<?> clazz) throws NoSuchMethodException {
        Method propertyReadMethod = null;
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            propertyReadMethod = clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        if (propertyReadMethod == null) {
            methodName = "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            try {
                propertyReadMethod = clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }
        if (propertyReadMethod == null) {
            // boolean property named isXxx read property is isXxx, not isIsXxx
            methodName = propertyName;
            // if no such get method at all, throw NoSuchMethodException.
            propertyReadMethod = clazz.getMethod(methodName);
        }
        checkArgument(propertyReadMethod.getParameterCount() == 0, "get method can not have args");
        return propertyReadMethod;
    }

    protected String expandVars(MultiValueMap<String, String> form) {
        checkNotNull(form);
        return form.entrySet().stream()
                .map(pair -> pair.getValue().stream()
                        .map(value -> pair.getKey() + "=" + String.valueOf(value))
                        .collect(Collectors.joining("&")))
                .collect(Collectors.joining("&"));
    }

    protected HttpHeaders addReqContentType(HttpHeaders httpHeaders, String mediaType) {
        HttpHeaders newHttpHeaders = new HttpHeaders();
        if (Objects.nonNull(httpHeaders)) {
            newHttpHeaders.putAll(httpHeaders);
        }
        newHttpHeaders.set(HttpHeaders.CONTENT_TYPE, mediaType);
        return newHttpHeaders;
    }

    protected <T> ResponseEntity<T> execute(String url,
                                            HttpMethod httpMethod,
                                            HttpEntity<?> httpEntity,
                                            ParameterizedTypeReference<T> typeReference,
                                            Map<String, String> uriVariables) {
        if (Objects.isNull(uriVariables)) {
            uriVariables = Maps.newHashMap();
        }

        if (BooleanUtils.isTrue(swallowException)) {
            try {
                return doExecute(url, httpMethod, httpEntity, typeReference, uriVariables);
            } catch (Exception e) {
                // camouflage exception
                log.error("http call failed, url [{}], http method [{}], http entity [{}], uri vars [{}]",
                        url, httpMethod, httpEntity, uriVariables, e);
                return doCamouflageException();
            }
        } else {
            return doExecute(url, httpMethod, httpEntity, typeReference, uriVariables);
        }
    }

    protected <T> ResponseEntity<T> doExecute(String url,
                                              HttpMethod httpMethod,
                                              HttpEntity<?> httpEntity,
                                              ParameterizedTypeReference<T> typeReference,
                                              Map<String, String> uriVariables) {
        return restTemplate.exchange(url, httpMethod, httpEntity, typeReference, uriVariables);
    }

    protected <T> ResponseEntity<T> doCamouflageException() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> T orElseGet(@Nonnull ResponseEntity<T> responseEntity,
                           @Nonnull Supplier<T> defaultValueSupplier) {
        checkNotNull(responseEntity);
        checkNotNull(defaultValueSupplier);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return defaultValueSupplier.get();
        }
    }

    public <T> T orElseThrow(@Nonnull ResponseEntity<T> responseEntity,
                             @Nonnull Supplier<RuntimeException> exceptionSupplier) {
        checkNotNull(responseEntity);
        checkNotNull(exceptionSupplier);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw exceptionSupplier.get();
        }
    }

}
