package com.ysu.zyw.tc.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
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
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("tc api docs")
                                .description("tc api docs")
                                .license("MIT")
                                .licenseUrl("https://github.com/srarcbrsent/tc/blob/master/LICENSE")
                                .termsOfServiceUrl("https://github.com/srarcbrsent/tc")
                                .version("1.0")
                                .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

}
