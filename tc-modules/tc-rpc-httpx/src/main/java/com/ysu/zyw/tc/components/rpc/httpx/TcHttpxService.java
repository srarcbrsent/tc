package com.ysu.zyw.tc.components.rpc.httpx;

import com.google.common.collect.Maps;
import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.base.ex.TcException;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
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
 *
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
    private boolean camouflageException;

    public <T> ResponseEntity<T> getText4Entity(@Nonnull String url,
                                                @Nullable Map<String, String> pathVariables,
                                                @Nullable HttpHeaders httpHeaders,
                                                @Nullable MultiValueMap<String, String> requestBody,
                                                @Nonnull ParameterizedTypeReference<T> typeReference) {
        checkNotNull(url);
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
        if (obj instanceof Collection ||
                obj.getClass().isArray() ||
                ClassUtils.isPrimitiveOrWrapper(obj.getClass()) ||
                obj instanceof String) {
            throw new TcException("list / primitive / string value is not supported");
        }

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        copy2MultiValueMap(multiValueMap, TcConstant.Str.BLANK, obj);
        return multiValueMap;
    }

    protected void copy2MultiValueMap(MultiValueMap<String, String> multiValueMap,
                                      String currentPath,
                                      Object obj) {
        if (Objects.isNull(obj)) {
            return;
        }
        if (ClassUtils.isPrimitiveOrWrapper(obj.getClass()) || obj instanceof String) {
            // is primitive type
            multiValueMap.set(currentPath, urlEncode(String.valueOf(obj)));
        } else if (obj instanceof Map) {
            // is map
            Map<?, ?> rValue = (Map) obj;
            rValue.entrySet().forEach(entry -> {
                checkArgument(entry.getKey() instanceof String, "map key must is string");
                String key = String.valueOf(entry.getKey());
                String oKey = StringUtils.hasText(currentPath) ?
                        (currentPath + TcConstant.Str.DOT + key) : key;
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
                    String oKey = StringUtils.hasText(currentPath) ?
                            (currentPath + TcConstant.Str.DOT + key) : key;
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
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                propertyName.substring(1);
        try {
            propertyReadMethod = clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        if (propertyReadMethod == null) {
            methodName = "is" + propertyName.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                    propertyName.substring(1);
            propertyReadMethod = clazz.getMethod(methodName);
        }
        checkArgument(propertyReadMethod.getParameterCount() == 0, "get method can not have args");
        return propertyReadMethod;
    }

    protected String expandVars(MultiValueMap<String, String> form) {
        checkNotNull(form);
        return form.entrySet().stream()
                .map(pair -> pair.getValue().stream()
                        .map(value -> pair.getKey() + "=" + urlEncode(value))
                        .collect(Collectors.joining("&")))
                .collect(Collectors.joining("&"));
    }

    @SneakyThrows
    public String urlEncode(String value) {
        return URLEncoder.encode(value, "UTF-8");
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

        if (BooleanUtils.isTrue(camouflageException)) {
            try {
                return doExecute(url, httpMethod, httpEntity, typeReference, uriVariables);
            } catch (Exception e) {
                // camouflage exception
                log.error("", e);
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
        Date now = new Date();

        if (log.isInfoEnabled()) {
            log.info("start call http api [{}:{}], url vars [{}], request body [{}]",
                    httpMethod, url, uriVariables, httpEntity);
        }

        ResponseEntity<T> responseEntity =
                restTemplate.exchange(url, httpMethod, httpEntity, typeReference, uriVariables);

        if (log.isInfoEnabled()) {
            log.info("call http api [{}:{}] finish, response code [{}], take time [{}]",
                    httpMethod, url, httpEntity, TcDateUtils.duration(now, new Date()));
        }

        return responseEntity;
    }

    protected <T> ResponseEntity<T> doCamouflageException() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> T orElseGet(@NotNull ResponseEntity<T> responseEntity,
                           @Nullable T value) {
        checkNotNull(responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return value;
        }
    }

    public <T> T orElseThrow(@NotNull ResponseEntity<T> responseEntity,
                             @NotNull Supplier<RuntimeException> supplier) {
        checkNotNull(responseEntity);
        checkNotNull(supplier);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw supplier.get();
        }
    }

}