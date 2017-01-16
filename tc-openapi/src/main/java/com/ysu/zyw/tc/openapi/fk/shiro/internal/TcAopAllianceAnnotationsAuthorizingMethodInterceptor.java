package com.ysu.zyw.tc.openapi.fk.shiro.internal;

import com.ysu.zyw.tc.openapi.fk.shiro.enhance.TcRequiresRolesDynamicAnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.*;
import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import java.util.ArrayList;
import java.util.List;

public class TcAopAllianceAnnotationsAuthorizingMethodInterceptor
        extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public TcAopAllianceAnnotationsAuthorizingMethodInterceptor() {
        List<AuthorizingAnnotationMethodInterceptor> interceptors =
                new ArrayList<AuthorizingAnnotationMethodInterceptor>(5);

        //use a Spring-specific Annotation resolver - Spring's AnnotationUtils is nicer than the
        //raw JDK resolution process.
        AnnotationResolver resolver = new SpringAnnotationResolver();
        //we can re-use the same resolver instance - it does not retain state:
        interceptors.add(new RoleAnnotationMethodInterceptor(resolver));
        interceptors.add(new PermissionAnnotationMethodInterceptor(resolver));
        interceptors.add(new AuthenticatedAnnotationMethodInterceptor(resolver));
        interceptors.add(new UserAnnotationMethodInterceptor(resolver));
        interceptors.add(new GuestAnnotationMethodInterceptor(resolver));
        // customize annotations
        interceptors.add(new TcRequiresRolesDynamicAnnotationMethodInterceptor(resolver));
        setMethodInterceptors(interceptors);
    }

}
