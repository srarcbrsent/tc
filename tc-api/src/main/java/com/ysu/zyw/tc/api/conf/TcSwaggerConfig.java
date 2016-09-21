package com.ysu.zyw.tc.api.conf;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class TcSwaggerConfig {

    @Getter
    @Setter
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(
                        RequestMethod.GET,
                        Lists.newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.OK.value())
                                        .message("请求成功")
                                        .responseModel(new ModelRef("com.ysu.zyw.tc.model.c.TcR"))
                                        .build()
                        )
                )
                .globalResponseMessage(
                        RequestMethod.POST,
                        Lists.newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.OK.value())
                                        .message("请求成功")
                                        .responseModel(new ModelRef("com.ysu.zyw.tc.model.c.TcR"))
                                        .build()
                        )
                )
                .protocols(Sets.newHashSet("http", "https"));
    }

}
