package com.louddt.tag.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfiguration {
    private String api;
    private String title;
    private String description;
    private String version;
    private String author;
    private String gmt;

    @Bean
    @Primary
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(api))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfo api = new ApiInfoBuilder()
                .title(title)
                .description(description())
                .version(version)
                .build();
        log.info("\n*** Initialize Swagger2 RESTful API successful: " + this.api);
        return api;
    }

    private String description() {
        StringBuilder su = new StringBuilder("温馨提示：");
        su.append(description).append("，作者：").append(author).append("，创建时间：").append(gmt).append("(●'◡'●)。\n");
        return su.toString();
    }


    public String getApi() {
        return api;
    }
    public void setApi(String api) {
        this.api = api;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGmt() {
        return gmt;
    }
    public void setGmt(String gmt) {
        this.gmt = gmt;
    }
}
