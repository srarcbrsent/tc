package com.ysu.zyw.tc.openapi.fk.shiro.enhance;

import com.ysu.zyw.tc.openapi.fk.shiro.support.TcRequiresRolesDynamicSup;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

@Slf4j
public class TcRequiresRolesDynamicAnnotationHandler extends AuthorizingAnnotationHandler {

    @Resource
    private TcRequiresRolesDynamicSup tcRequiresRolesDynamicSup;

    public TcRequiresRolesDynamicAnnotationHandler() {
        super(TcRequiresRolesDynamic.class);
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof TcRequiresRolesDynamic)) {
            return;
        }

        TcRequiresRolesDynamic rrdAnnotation = (TcRequiresRolesDynamic) a;
        String[] roles = rrdAnnotation.value();

        if (!tcRequiresRolesDynamicSup.isAccessAllowed(roles, rrdAnnotation.logical())) {
            throw new AuthorizationException();
        }
    }

}
