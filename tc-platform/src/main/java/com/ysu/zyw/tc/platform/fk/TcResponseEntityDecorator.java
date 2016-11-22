package com.ysu.zyw.tc.platform.fk;

import com.ysu.zyw.tc.model.mw.TcExtra;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Order(value = -500)
@Slf4j
public class TcResponseEntityDecorator {

    @Pointcut(value = "execution(public org.springframework.http.ResponseEntity com.ysu.zyw.tc.platform.web..*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut() {
    }

    /**
     * 1 => 如果内部抛出了任何异常，封装为TcR返回，extra必须有可直接展现的描述信息
     * 2 => 如果状态码不为200，extra必须有可直接展现的描述信息，如果没有附上默认值
     */
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        try {
            ResponseEntity responseEntity = (ResponseEntity) proceedingJoinPoint.proceed();
            TcR<?> tcR = (TcR<?>) responseEntity.getBody();
            camouflageResponse(tcR);
            return responseEntity;
        } catch (Exception e) {
            // 如果内部抛出了异常 则对页面返回 500 服务器异常
            log.error("[{}][{}][{}]", "OpenApi切面-服务器异常", "切面异常捕获", proceedingJoinPoint.getArgs(), e);
            TcR<Object> tcR = determineResponse(method).setCode(TcR.R.SERVER_ERROR);
            camouflageResponse(tcR);
            return ResponseEntity.ok(tcR);
        }
    }

    private void camouflageResponse(TcR<?> tcR) {
        if (!tcR.isSuccess()) {
            if (Objects.isNull(tcR.getExtra())) {
                tcR.setExtra(new TcExtra());
            }
            if (Objects.isNull(tcR.getExtra().getDescription())) {
                tcR.getExtra().setDescription("系统异常，请稍后再试！");
            }
        }
    }

    // generic type with jackson serialization, do not cause any problem, if use other serialization, may failed.
    private <T> TcR<T> determineResponse(Method method) {
        return new TcR<>();
    }

}
