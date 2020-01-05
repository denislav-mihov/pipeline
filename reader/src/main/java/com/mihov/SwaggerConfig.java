package com.mihov;

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
 * Created by Denis on 05-Jan-20.
 *
 * @author Denis
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.mihov.api"))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder()
      .title("Pipeline Reader API")
      .description("Read all persisted messages from the database")
      .contact(new Contact("Denislav Mihov", "https://github.com/denislav-mihov", "dmikhov@gmail.com"))
      .version("1.0")
      .build();
  }
}
