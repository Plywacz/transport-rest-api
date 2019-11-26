package org.mplywacz.transitapi.config;
/*
Author: BeGieU
Date: 09.11.2019
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mplywacz.transitapi"))
                .paths(regex("/api.*")) //select endpoints
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo("Documentation for Transport Rest Api",
                "Simple trasport api",
                "0.1",
                "Terms of Service",
                new Contact("Marcin Plywacz",
                        " ",
                        "m.plywacz@interia.pl"),
                "All rights reserved",
                null);
    }
}
