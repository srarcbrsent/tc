package com.ysu.zyw.tc.api.fk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
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
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("TcApi")
                                .description("TcApiDescription")
                                .contact(new Contact("yaowu.zhang", null, "zhang_yao_wu1993@yeah.net"))
                                .license("MIT License")
                                .licenseUrl("https://github.com/srarcbrsent/tc/blob/master/LICENSE")
                                .version("1.0")
                                .build())
                .pathMapping("/")
                .host("apiimpl.tc.com")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(
                        RequestMethod.GET,
                        Lists.newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.OK.value())
                                        .message("请求成功")
                                        .responseModel(new ModelRef("TcR"))
                                        .build()
                        )
                )
                .globalResponseMessage(
                        RequestMethod.POST,
                        Lists.newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.OK.value())
                                        .message("请求成功")
                                        .responseModel(new ModelRef("TcR"))
                                        .build()
                        )
                )
                .protocols(Sets.newHashSet("http", "https"));
    }

}
