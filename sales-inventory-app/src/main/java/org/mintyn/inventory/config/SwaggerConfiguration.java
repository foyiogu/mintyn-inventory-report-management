package org.mintyn.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
class SwaggerConfiguration implements WebMvcConfigurer {
// WEBSITE IS - http://localhost:8080/swagger-ui/

    @Bean
    public Docket swaggerConfig() {
        //Return a prepared Docket Instance
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mintyn"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Sales Inventory App",
                "API for Mintyn Inventory",
                "1.0-SNAPSHOT",
                "Free to use",
                new Contact("Mintyn", "", "hr@mintyn.com"),
                "API License",
                "",
                Collections.emptyList()
        );
    }



}
