package com.woori.codeshare.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI wooriCodeShareAPI() {
        Info info = new Info()
                .title("Woori-Code-Share API")
                .description("Woori-Code-Share API 명세서")
                .version("1.0.0");

        //JWT 인증 관련 설정
        /*
        String jwtSchemeName = "JWT TOKEN";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(
                        jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP) // HTTP 방식
                                .scheme("bearer")
                                .bearerFormat("JWT"));
         */

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
//                .addSecurityItem(securityRequirement)
//                .components(components);
    }
}
