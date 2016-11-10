package com.ysu.zyw.tc.api.fk;

import com.ysu.zyw.tc.api.fk.ex.TcVerifyFailureException;
import com.ysu.zyw.tc.base.utils.TcDateUtils;
import com.ysu.zyw.tc.mw.TcP;
import com.ysu.zyw.tc.mw.TcR;
import com.ysu.zyw.tc.sys.ex.TcResourceConflictException;
import com.ysu.zyw.tc.sys.ex.TcResourceNotFoundException;
import com.ysu.zyw.tc.validator.TcValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ClassUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Aspect
@Order(value = -500)
@Slf4j
public class TcExceptionResponseDecorator {

    @Pointcut(value = "execution(public * com.ysu.zyw.tc.api.impl..*(..))")
    public void pointcut() {}

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
        try {
            log.info("call open apiimpl [{}]", proceedingJoinPoint.getSignature().getName());
            Date startTime = Calendar.getInstance().getTime();
            Object result = proceedingJoinPoint.proceed();
            log.info("call open apiimpl [{}] finish, take time [{}]", proceedingJoinPoint.getSignature().getName(),
                    TcDateUtils.duration(startTime, new Date()));
            if (Objects.isNull(result)) {
                log.warn("[{}][{}][{}]", "OpenApi切面-请求无任何返回值", "切面异常捕获", proceedingJoinPoint.getArgs());
                return determineResponse(method).setCode(TcR.R.NOT_FOUND).setDescription(TcR.R.NOT_FOUND_DESCRIPTION);
            }
            return result;
        } catch (TcResourceNotFoundException e) {
            // 如果内部抛出了资源不存在异常 则对页面返回 404 资源不存在
            log.error("[{}][{}][{}]", "OpenApi切面-资源不存在", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.NOT_FOUND).setDescription(TcR.R.NOT_FOUND_DESCRIPTION);
        } catch (TcResourceConflictException e) {
            // 如果内部抛出了资源冲突异常 则对页面返回 409 冲突异常
            log.error("[{}][{}][{}]", "OpenApi切面-资源冲突", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.CONFLICT).setDescription(TcR.R.CONFLICT_DESCRIPTION);
        } catch (TcVerifyFailureException e) {
            // 如果内部抛出了验证错误异常 则对页面返回 422 无法处理的请求
            TcValidator.TcVerifyFailure tcVerifyFailure = e.getTcVerifyFailure();
            log.error("[{}][{}][{}][{}]", "OpenApi切面-无法处理的请求", "切面异常捕获", proceedingJoinPoint.getArgs(),
                    tcVerifyFailure, e);
            return determineResponse(method).setCode(TcR.R.UNPROCESSABLE_ENTITY)
                    .setDescription(TcR.R.UNPROCESSABLE_ENTITY_DESCRIPTION).setExtra(tcVerifyFailure);
        } catch (Exception e) {
            // 如果内部抛出了异常 则对页面返回 500 服务器异常
            log.error("[{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            return determineResponse(method).setCode(TcR.R.SERVER_ERROR).setDescription(TcR.R.SERVER_ERROR_DESCRIPTION);
        }
    }

    private <T, E> TcR<T, E> determineResponse(Method method) {
        return ClassUtils.isAssignable(method.getReturnType(), TcP.class) ? new TcP() : new TcR();
    }

}
