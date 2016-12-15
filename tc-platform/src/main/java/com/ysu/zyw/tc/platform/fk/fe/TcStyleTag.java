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
public class TcStyleTag implements TemplateDirectiveModel {

    private static final String STYLE_HREF = "href";

    private static final String STYLE_TAG =
            "<link href=\"{}\" type=\"text/css\" rel=\"stylesheet\"/>";

    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Object oStyleHref = params.get(STYLE_HREF);
        if (Objects.isNull(oStyleHref)) {
            log.error("style tag must set href property, ignore style tag render.");
            return;
        }

        String staticBase = env.getConfiguration().getSharedVariable("staticBase").toString();
        String processedStyleHref = doProcess(staticBase, oStyleHref.toString());

        env.getOut().write(processedStyleHref);
    }

    private String doProcess(String staticBase, String href) {
        return TcFormatUtils.format(STYLE_TAG, staticBase + href);
    }

}
