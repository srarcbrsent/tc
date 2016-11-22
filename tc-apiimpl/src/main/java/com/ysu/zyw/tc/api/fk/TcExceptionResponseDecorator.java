package com.ysu.zyw.tc.api.fk;

import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.model.mw.TcExtra;
import com.ysu.zyw.tc.model.mw.TcR;
import com.ysu.zyw.tc.sys.ex.TcResourceConflictException;
import com.ysu.zyw.tc.sys.ex.TcResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
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
     * 1 => 如果内部抛出了任何异常，封装为TcR返回
     * 2 => 如果状态码为422 则必须有extra描述情况，如果没有设置默认值
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        try {
            log.info("call open apiimpl [{}]", proceedingJoinPoint.getSignature().getName());
            Date startTime = new Date();
            Object result = proceedingJoinPoint.proceed();
            log.info("call open apiimpl [{}] finish, take time [{}]", proceedingJoinPoint.getSignature().getName(),
                    TcDateUtils.duration(startTime, new Date()));
            if (Objects.isNull(result)) {
                log.warn("[{}][{}][{}]", "OpenApi切面-请求无任何返回值", "切面异常捕获", proceedingJoinPoint.getArgs());
                return determineResponse(method).setCode(TcR.R.NOT_FOUND);
            }
            return result;
        } catch (TcResourceNotFoundException e) {
            // 如果内部抛出了资源不存在异常 则对页面返回 404 资源不存在
            log.error("[{}][{}][{}]", "OpenApi切面-资源不存在", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.NOT_FOUND);
        } catch (TcResourceConflictException e) {
            // 如果内部抛出了资源冲突异常 则对页面返回 409 冲突异常
            log.error("[{}][{}][{}]", "OpenApi切面-资源冲突", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.CONFLICT);
        } catch (TcUnProcessableEntityException e) {
            // 如果内部抛出了验证错误异常 则对页面返回 422 无法处理的请求
            TcExtra tcExtra = e.getTcExtra();
            log.warn("[{}][{}][{}][{}]", "OpenApi切面-无法处理的请求-业务级异常", "切面异常捕获",
                    proceedingJoinPoint.getArgs(), tcExtra);
            return determineResponse(method).setCode(TcR.R.UNPROCESSABLE_ENTITY).setExtra(tcExtra);
        } catch (Exception e) {
            // 如果内部抛出了异常 则对页面返回 500 服务器异常
            log.error("[{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.SERVER_ERROR);
        }
    }

    // generic type with jackson serialization, do not cause any problem, if use other serialization, may failed.
    private <T> TcR<T> determineResponse(Method method) {
        return new TcR<>();
    }

}
