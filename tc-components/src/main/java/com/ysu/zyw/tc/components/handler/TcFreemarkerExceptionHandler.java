package com.ysu.zyw.tc.components.handler;

import com.ysu.zyw.tc.base.ex.TcException;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.Writer;

@Slf4j
public class TcFreemarkerExceptionHandler implements TemplateExceptionHandler {

    @SneakyThrows
    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        log.error("Unexpected error occurred in freemarker process.", te);

        out.close();

        // let the container process it.
        throw new TcException(te);
    }

}
