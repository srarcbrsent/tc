package com.ysu.zyw.tc.api.fk;

import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.Objects;

@Aspect
@Order(value = -500)
@Slf4j
public class TcExceptionResponseDecorator {

    @Pointcut(value = "execution(public * com.ysu.zyw.tc.api.impl..*(..))")
    public void pointcut() {
    }

    /**
     * 1 => 如果内部抛出了TcUnProcessableEntityException 包装并返回对应code
     * 2 => 如果内部抛出了任何异常 包装为R.SERVER_ERROR返回
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            log.info("call open apiimpl [{}]", proceedingJoinPoint.getSignature().getName());
            Date startTime = new Date();
            Object result = proceedingJoinPoint.proceed();
            log.info("call open apiimpl [{}] finish, take time [{}]", proceedingJoinPoint.getSignature().getName(),
                    TcDateUtils.duration(startTime, new Date()));
            if (Objects.isNull(result)) {
                log.warn("open apiimpl [{}] return a null object", proceedingJoinPoint.getSignature().getName());
            }
            return result;
        } catch (TcUnProcessableEntityException e) {
            // 如果内部抛出了无法处理的请求异常 直接包装返回值
            int code = e.getCode();
            String description = e.getDescription();
            Object extra = e.getExtra();
            log.warn("[{}][{}][{}][{}][{}][{}]", "OpenApi切面-无法处理的请求-业务级异常", "切面异常捕获",
                    proceedingJoinPoint.getSignature().getName(),
                    proceedingJoinPoint.getArgs(), code, description);
            return TcR.code(code, description).setExtra(extra);
        } catch (Exception e) {
            // 如果内部抛出了异常 则对页面返回 500 服务器异常
            log.error("[{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return TcR.ex();
        }
    }

}
