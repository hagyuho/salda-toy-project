package com.salda.saldahomework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com")).build();
	}

	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder().title("Spring API Documentation").description("").license("License Of API")
				.licenseUrl("API license URL").version("1").build();
	}
}
