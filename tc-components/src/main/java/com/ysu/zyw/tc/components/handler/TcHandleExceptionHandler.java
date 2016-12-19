package com.ysu.zyw.tc.components.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ClassUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class TcHandleExceptionHandler implements HandlerExceptionResolver {

    @Getter
    @Setter
    private String view5xx;

    @Getter
    @Setter
    private String ajax5xx;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {

        log.error("Unexpected error occurred in handle request.", ex);

        // if handler is instance of HandlerMethod then it's a http request,
        // otherwise it's a websocket request or other service.
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            boolean returnResponseEntity = ClassUtils.isAssignable(handlerMethod.getMethod().getReturnType(),
                    ResponseEntity.class);
            boolean hasResponseBodyAnnotation = Objects.nonNull(handlerMethod.getMethodAnnotation(ResponseBody.class));

            if (returnResponseEntity || hasResponseBodyAnnotation) {
                return new ModelAndView(new RedirectView(view5xx));
            } else {
                return new ModelAndView(new RedirectView(ajax5xx));
            }
        }

        return null;
    }

}
