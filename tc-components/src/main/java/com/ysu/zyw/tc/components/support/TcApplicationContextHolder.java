package com.ysu.zyw.tc.components.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TcApplicationContextHolder is a class used for get current application context static,
 * and it's must be register to one application context (can be web application context or other),
 * and it's will hold the current application context, and in any place u can use it's field.
 *
 * @author yaowu.zhang
 */
@Slf4j
public class TcApplicationContextHolder implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("inject application context to application context holder ...");
        TcApplicationContextHolder.applicationContext = applicationContext;
    }

}
