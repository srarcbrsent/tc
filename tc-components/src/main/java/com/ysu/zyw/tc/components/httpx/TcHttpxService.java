package com.ysu.zyw.tc.components.httpx;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import com.ysu.zyw.tc.sys.ex.TcException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcHttpxService {

    @Getter
    @Setter
    @Resource
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> getText4Entity(@Nonnull String url,
                                                @Nullable Map<String, String> pathVariables,
                                                @Nullable HttpHeaders httpHeaders,
                                                @Nullable MultiValueMap<String, String> requestBody,
                                                @Nonnull ParameterizedTypeReference<T> typeReference) {
        checkNotNull(url);
        checkNotNull(typeReference);
        HttpEntity<Void> httpEntity = new HttpEntity<>(null,
                this.addReqContentType(httpHeaders, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        String expandVars = this.expandVars(requestBody);
        String expandUrl = StringUtils.hasText(expandVars) ? (url + "?" + expandVars) : url;
        return this.execute(expandUrl, HttpMethod.GET, httpEntity, typeReference, pathVariables);
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
                this.addReqContentType(httpHeaders, MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        return this.execute(url, HttpMethod.POST, httpEntity, typeReference, pathVariables);
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
                this.addReqContentType(httpHeaders, MediaType.APPLICATION_JSON_UTF8_VALUE));
        return this.execute(url, HttpMethod.POST, httpEntity, typeReference, pathVariables);
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
            multiValueMap.set(currentPath, String.valueOf(obj));
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
                    Throwables.propagate(e);
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
                        .map(value -> pair.getKey() + "=" + value)
                        .collect(Collectors.joining("&")))
                .collect(Collectors.joining("&"));
    }

    protected HttpHeaders addReqContentType(HttpHeaders httpHeaders, String mediaType) {
        HttpHeaders newHttpHeaders = new HttpHeaders();
        newHttpHeaders.putAll(httpHeaders);
        newHttpHeaders.set(HttpHeaders.CONTENT_TYPE, mediaType);
        return newHttpHeaders;
    }

    protected <T> ResponseEntity<T> execute(String url,
                                            HttpMethod httpMethod,
                                            HttpEntity<?> httpEntity,
                                            ParameterizedTypeReference<T> typeReference,
                                            Map<String, String> uriVariables) {
        if (log.isInfoEnabled()) {
            log.info("start call api [{}:{}], url vars [{}], request body [{}]",
                    httpMethod, url, uriVariables, httpEntity);
        }

        LocalDateTime now = LocalDateTime.now();
        ResponseEntity<T> responseEntity =
                restTemplate.exchange(url, httpMethod, httpEntity, typeReference, uriVariables);

        if (log.isInfoEnabled()) {
            log.info("call api [{}:{}] finish, response code [{}], has body [{}], take time [{}s]",
                    httpMethod, url, Objects.nonNull(responseEntity.getBody()), httpEntity,
                    Duration.between(now, LocalDateTime.now()).get(ChronoUnit.SECONDS));
        }

        return responseEntity;
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
