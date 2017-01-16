package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class TcRequiresRolesDynamicAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    public TcRequiresRolesDynamicAnnotationMethodInterceptor(AuthorizingAnnotationHandler handler,
                                                             AnnotationResolver resolver) {
        super(handler, resolver);
    }

}
