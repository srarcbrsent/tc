package com.ysu.zyw.tc.openapi.fk.shiro.internal;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.*;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TcAopAllianceAnnotationsAuthorizingMethodInterceptor
        extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public TcAopAllianceAnnotationsAuthorizingMethodInterceptor(
            AnnotationResolver resolver,
            List<AuthorizingAnnotationMethodInterceptor> extensionInterceptors) {
        List<AuthorizingAnnotationMethodInterceptor> interceptors =
                new ArrayList<>(5 + extensionInterceptors.size());
        //use a Spring-specific Annotation resolver - Spring's AnnotationUtils is nicer than the
        //raw JDK resolution process.
        //we can re-use the same resolver instance - it does not retain state:
        interceptors.add(new RoleAnnotationMethodInterceptor(resolver));
        interceptors.add(new PermissionAnnotationMethodInterceptor(resolver));
        interceptors.add(new AuthenticatedAnnotationMethodInterceptor(resolver));
        interceptors.add(new UserAnnotationMethodInterceptor(resolver));
        interceptors.add(new GuestAnnotationMethodInterceptor(resolver));
        // customize annotations
        if (CollectionUtils.isNotEmpty(extensionInterceptors)) {
            interceptors.addAll(extensionInterceptors);
        }
        log.info("successful inject [{}] customize annotation interceptors ...", interceptors.size());
        setMethodInterceptors(interceptors);
    }

}
