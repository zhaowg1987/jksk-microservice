package com.jksk.apidocument.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author
 * @create 2019-07-03
 **/
@Configuration
public class ApiDocConfig {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.jksk";
    public static final String VERSION = "0.0.1";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("九课数科--API文档")
                .description("-------------九课数科--API文档-------------------")
                .contact(new Contact("zhaowg","https://www.jiukeshuke.com","zhaoweigang@zhsoftbank.com"))
                .version(VERSION)
                .build();
    }

}
