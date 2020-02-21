package com.roylao.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo intiApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "spring-boot-redis-quartz服务API",
                intitContextInfo(),
                "1.0.0",
                "服务条款", "Roylao",
                "spring-boot-redis-quartz",
                "");
        return apiInfo;
    }

    private String intitContextInfo(){
        return "test";
    }

    @Bean
    public Docket restfulApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("RestfulApi")
                .apiInfo(intiApiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.roylao.controller"))
                .build();
    }

}
