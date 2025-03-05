package kit.se.capstone2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info()
				.version("v1.0")
				.title("LC Inside API")
				.description("""
								레전드 캡스톤 디자인2 프로젝트
								AI 어시스턴트 기반 법률 상담 커뮤니티 플랫폼
								LC Inside API 문서
						""");
		return new OpenAPI().info(info);
	}

//	@Bean
//	public OpenAPI openAPI(){
//		String securityJwtName = "JWT";
//		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
//		Components components = new Components()
//				.addSecuritySchemes(
//						securityJwtName, new SecurityScheme().name(securityJwtName)
//								.type(SecurityScheme.Type.HTTP)
//								.scheme("Bearer")
//								.bearerFormat(securityJwtName)
//				);
//
//		return new OpenAPI().addSecurityItem(securityRequirement).components(components);
//	}

}
