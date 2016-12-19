package com.ysu.zyw.tc.components.freemarker.support;

import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Nonnull;
import java.io.StringWriter;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * inject this class to spring framework required
 */
@Slf4j
public class TcFreemarkerRender {

    @Getter
    @Setter
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @SneakyThrows
    public String render(@Nonnull String templatePath, @Nonnull Map<String, Object> params) {
        checkNotNull(freeMarkerConfigurer);
        checkNotNull(templatePath);
        checkNotNull(params);
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(params, stringWriter);
            log.info("render template [{}]", templatePath);
            return stringWriter.toString();
        }
    }

}
