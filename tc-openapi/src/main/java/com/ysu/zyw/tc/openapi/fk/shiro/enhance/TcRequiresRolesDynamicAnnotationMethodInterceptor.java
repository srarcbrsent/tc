package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class TcRequiresRolesDynamicAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    public TcRequiresRolesDynamicAnnotationMethodInterceptor() {
        super(new TcRequiresRolesDynamicAnnotationHandler());
    }

    public TcRequiresRolesDynamicAnnotationMethodInterceptor(
            AnnotationResolver resolver) {
        super(new TcRequiresRolesDynamicAnnotationHandler(), resolver);
    }

}
