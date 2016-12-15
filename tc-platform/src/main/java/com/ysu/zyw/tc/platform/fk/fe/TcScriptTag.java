package com.ysu.zyw.tc.platform.fk.fe;

import com.ysu.zyw.tc.base.utils.TcFormatUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TcScriptTag implements TemplateDirectiveModel {

    private static final String SCRIPT_SRC = "src";

    private static final String SCRIPT_TAG =
            "<script src=\"{}\" type=\"application/javascript\"></script>";

    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Object oScriptSrc = params.get(SCRIPT_SRC);
        if (Objects.isNull(oScriptSrc)) {
            log.error("script tag must set src property, ignore script tag render.");
            return;
        }

        String staticBase = env.getConfiguration().getSharedVariable("staticBase").toString();
        String processedScriptSrc = doProcess(staticBase, oScriptSrc.toString());

        env.getOut().write(processedScriptSrc);
    }

    private String doProcess(String staticBase, String src) {
        return TcFormatUtils.format(SCRIPT_TAG, staticBase + src);
    }

}
