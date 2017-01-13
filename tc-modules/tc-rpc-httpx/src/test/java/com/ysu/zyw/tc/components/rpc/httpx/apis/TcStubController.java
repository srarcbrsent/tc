package com.ysu.zyw.tc.components.rpc.httpx.apis;

import com.ysu.zyw.tc.base.utils.TcSerializationUtils;
import com.ysu.zyw.tc.components.converters.TcDateFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class TcStubController implements EmbeddedServletContainerCustomizer {

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TcStubs {

        private int id;

        private Date now;

    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> messageConverter =
                new MappingJackson2HttpMessageConverter(TcSerializationUtils.OBJECT_MAPPER);
        return new HttpMessageConverters(messageConverter);
    }

    @Bean
    public TcDateFormatter customFormatters() {
        return new TcDateFormatter();
    }

    @RequestMapping("/getText")
    public TcStubs stubGet(TcStubs tcStubs) {
        return tcStubs;
    }

    @RequestMapping("/postText")
    public TcStubs stubPostText(TcStubs tcStubs) {
        return tcStubs;
    }

    @RequestMapping("/postJson")
    public TcStubs stubPostJson(@RequestBody TcStubs tcStubs) {
        return tcStubs;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(13006);
    }

    public static void main(String[] args) {
        SpringApplication.run(TcStubController.class, args);
    }

}