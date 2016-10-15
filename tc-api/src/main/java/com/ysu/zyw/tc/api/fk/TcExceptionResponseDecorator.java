package com.ysu.zyw.tc.api.fk;

import com.ysu.zyw.tc.api.fk.ex.TcResourceConflictException;
import com.ysu.zyw.tc.api.fk.ex.TcResourceNotFoundException;
import com.ysu.zyw.tc.model.c.TcR;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

@Aspect
@Order(value = -500)
@Slf4j
public class TcExceptionResponseDecorator {

    @Pointcut(value = "execution(public * com.ysu.zyw.tc.api.openapi..*(..))")
    public void pointcut() {}

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            log.info("call open api [{}]", proceedingJoinPoint.getSignature().getName());
            return proceedingJoinPoint.proceed();
        } catch (TcResourceNotFoundException e) {
            // 如果内部抛出了资源不存在异常 则对页面返回 404 资源不存在
            log.error("[{}][{}][{}]", "OpenApi切面-资源不存在", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return ResponseEntity.ok(new TcR<>(TcR.R.NOT_FOUND, TcR.R.NOT_FOUND_DESCRIPTION));
        } catch (TcResourceConflictException e) {
            // 如果内部抛出了资源冲突异常 则对页面返回 409 冲突异常
            log.error("[{}][{}][{}]", "OpenApi切面-资源冲突", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return ResponseEntity.ok(new TcR<>(TcR.R.CONFLICT, TcR.R.CONFLICT_DESCRIPTION));
        } catch (Exception e) {
            // 如果内部抛出了异常 则对页面返回 500 服务器异常
            log.error("[{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return ResponseEntity.ok(new TcR<>(TcR.R.SERVER_ERROR, TcR.R.SERVER_ERROR_DESCRIPTION));
        }
    }

}
