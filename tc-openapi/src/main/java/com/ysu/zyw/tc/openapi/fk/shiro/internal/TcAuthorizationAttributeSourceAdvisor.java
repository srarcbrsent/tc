package com.ysu.zyw.tc.openapi.fk.shiro.internal;

import com.ysu.zyw.tc.openapi.fk.shiro.enhance.TcRequiresRolesDynamic;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcAuthorizationAttributeSourceAdvisor extends AuthorizationAttributeSourceAdvisor {

    @SuppressWarnings("unchecked")
    private static final Class<? extends Annotation>[] EXTENSION_AUTHZ_ANNOTATION_CLASSES =
            new Class[]{
                    TcRequiresRolesDynamic.class
            };

    public TcAuthorizationAttributeSourceAdvisor(AopAllianceAnnotationsAuthorizingMethodInterceptor interceptor) {
        checkNotNull(interceptor);
        setAdvice(interceptor);
    }

    @Override
    public boolean matches(Method method, Class targetClass) {
        return super.matches(method, targetClass) || matchesExtensionAnnotations(method, targetClass);
    }

    private boolean matchesExtensionAnnotations(Method method, Class targetClass) {
        Method m = method;

        if (isExtensionAuthzAnnotationPresent(m)) {
            return true;
        }

        //The 'method' parameter could be from an interface that doesn't have the annotation.
        //Check to see if the implementation has it.
        if (targetClass != null) {
            try {
                //noinspection unchecked
                m = targetClass.getMethod(m.getName(), m.getParameterTypes());
                if (isExtensionAuthzAnnotationPresent(m)) {
                    return true;
                }
            } catch (NoSuchMethodException ignored) {
                //default return value is false.  If we can't find the method, then obviously
                //there is no annotation, so just use the default return value.
            }
        }

        return false;
    }

    private boolean isExtensionAuthzAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : EXTENSION_AUTHZ_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

}
