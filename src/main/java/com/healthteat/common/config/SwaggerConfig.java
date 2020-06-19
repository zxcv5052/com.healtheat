package com.healthteat.common.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi() {
        ArrayList<ResponseMessage> responseMessageStatus = Lists.newArrayList(
                new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(400).message("Invalid Request").build(),
                new ResponseMessageBuilder().code(401).message("No Permission").build(),
                new ResponseMessageBuilder().code(404).message("Not Found").build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").build()
        );
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.healthteat.web.controller"))
                .paths(PathSelectors.any())
                .build()
                // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않고 컨트롤러에서 명시
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageStatus)
                .globalResponseMessage(RequestMethod.POST, responseMessageStatus)
                .globalResponseMessage(RequestMethod.PUT, responseMessageStatus)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageStatus);
    }
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Healtheat 의 API 입니다.")
                .description("웹 개발시 사용되는 서버 API에 대한 연동 Document")
                .license("kyeong")
                .licenseUrl("http://dev-room.tistory.com")
                .version("1")
                .build();
    }
}
