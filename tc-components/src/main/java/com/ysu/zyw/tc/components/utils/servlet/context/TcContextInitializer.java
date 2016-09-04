package com.ysu.zyw.tc.components.utils.servlet.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class TcContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        TcContextLifeCycleHandler contextLifeCycleHandler = getContextLifeCycleHandler(sce);
        if (Objects.nonNull(contextLifeCycleHandler)) {
            log.info("spring root application context initialize finished, custom context init method start ...");
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(contextLifeCycleHandler.getClass());
            Arrays.stream(methods)
                    .filter(method ->
                            Objects.nonNull(AnnotationUtils.findAnnotation(method, TcInvokeContextInitialized.class)))
                    .filter(method ->
                            AnnotationUtils.findAnnotation(method, TcInvokeContextInitialized.class).autorun())
                    .filter(((TcContextInitializer) AopContext.currentProxy())::isZeroParameter)
                    .sorted((o1, o2) -> AnnotationUtils.findAnnotation(o1, TcInvokeContextInitialized.class).order() -
                            AnnotationUtils.findAnnotation(o2, TcInvokeContextInitialized.class).order())
                    .forEach(method ->
                            ((TcContextInitializer) AopContext.currentProxy()).execute(method,
                                    contextLifeCycleHandler));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        TcContextLifeCycleHandler contextLifeCycleHandler = getContextLifeCycleHandler(sce);
        if (Objects.nonNull(contextLifeCycleHandler)) {
            log.info("spring root application context initialize finished, custom context destroy method start ...");
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(contextLifeCycleHandler.getClass());
            Arrays.stream(methods)
                    .filter(method ->
                            Objects.nonNull(AnnotationUtils.findAnnotation(method, TcInvokeContextDestroyed.class)))
                    .filter(method ->
                            AnnotationUtils.findAnnotation(method, TcInvokeContextDestroyed.class).autorun())
                    .filter(((TcContextInitializer) AopContext.currentProxy())::isZeroParameter)
                    .sorted((o1, o2) -> AnnotationUtils.findAnnotation(o1, TcInvokeContextDestroyed.class).order() -
                            AnnotationUtils.findAnnotation(o2, TcInvokeContextDestroyed.class).order())
                    .forEach(method ->
                            ((TcContextInitializer) AopContext.currentProxy()).execute(method,
                                    contextLifeCycleHandler));
        }
    }

    private TcContextLifeCycleHandler getContextLifeCycleHandler(ServletContextEvent sce) {
        ApplicationContext applicationContext = (ApplicationContext)
                sce.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        checkNotNull(applicationContext, "null application context is not allowed");
        return applicationContext.getBean(TcContextLifeCycleHandler.class);
    }

    private boolean isZeroParameter(Method method) {
        if (method.getParameterCount() != 0) {
            log.error("method [{}.{}] must contain 0 args but get [{}]",
                    method.getDeclaringClass().getName(),
                    method.getName(),
                    method.getParameterCount());
            return false;
        } else {
            return true;
        }
    }

    private void execute(Method method, TcContextLifeCycleHandler contextLifeCycleHandler) {
        try {
            method.invoke(contextLifeCycleHandler);
        } catch (Exception e) {
            log.error("call method [{}.{}] boot method raised exception ...",
                    method.getDeclaringClass().getName(),
                    method.getName(),
                    e);
        }
        log.info("method [{}.{}][{}] boot method has been called ...",
                method.getDeclaringClass().getName(),
                method.getName(),
                AnnotationUtils.findAnnotation(method, TcInvokeContextInitialized.class).order());
    }

}
