package com.ysu.zyw.tc.components.utils.handler;

import com.google.common.base.Throwables;
import com.ysu.zyw.tc.sys.ex.TcException;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

@Slf4j
public class TcFreemarkerExceptionHandler implements TemplateExceptionHandler {

    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        log.error("Unexpected error occurred in freemarker process.", te);

        try {
            out.close();
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        // let the container process it.
        throw new TcException(te);
    }

}
