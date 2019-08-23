package com.spallya.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.spallya.bookservice.constants.StringConstants.*;

/**
 * Class containing the configurations required for Swagger
 *
 * @author Spallya Omar
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket libraryApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .tags(new Tag(BOOK_SWAGGER_TAG, BOOK_SWAGGER_TAG_DESC));
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                BOOK_SERVICE_REST_API,
                SWAGGER_PROJECT_DESC,
                VERSION,
                TERMS_OF_SERVICE,
                new Contact(AUTHOR, PROJECT_URL, AUTHOR_EMAIL),
                APACHE_LICENSE,
                LICENSE_URL);
    }
}