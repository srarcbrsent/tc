package com.ysu.zyw.tc.platform.fk;

import com.ysu.zyw.tc.base.constant.TcConstant;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

@Aspect
@Order(value = TcConstant.AspectOrder.EXCEPTION_DECORATOR_ASPECT_ORDER)
@Slf4j
public class TcResponseEntityDecorator {

    @Pointcut(value = "execution(public org.springframework.http.ResponseEntity com.ysu.zyw.tc.platform.web..*(..)) "
            + "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut() {
    }

    /**
     * 1 => 如果内部抛出了任何异常，封装为TcR返回
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            // 如果内部抛出了异常
            log.error("[{}][{}][{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获",
                    proceedingJoinPoint.getSignature().getName(), proceedingJoinPoint.getArgs(), e);
            return ResponseEntity.ok(TcR.errs());
        }
    }

}
