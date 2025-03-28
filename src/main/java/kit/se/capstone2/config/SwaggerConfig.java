package kit.se.capstone2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
		servers = {
				@Server(url = "http://202.31.202.38:80/api", description = "Production Server"),
				@Server(url = "http://localhost:8080/api", description = "Local Server")
		})
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		String jwtSchemeName = "bearerAuth";
		SecurityRequirement securityRequirement = new SecurityRequirement()
				.addList("bearerAuth");
		Components components = new Components()
				.addSecuritySchemes(
						jwtSchemeName, new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.in(SecurityScheme.In.HEADER).name("Authorization")
								.scheme("bearer")
								.bearerFormat("JWT")
				);
		Info info = new Info()
				.version("v1.0")
				.title("LC Inside API")
				.description("""
								레전드 캡스톤 디자인2 프로젝트
								AI 어시스턴트 기반 법률 상담 커뮤니티 플랫폼
								LC Inside API 문서
						
								---- 인증 관련 ----
								1. 아래쪽 login-endpoint를 통해 로그인을 하고 JWT 토큰을 받아서 오른쪽 Authorize 버튼을 눌러서 JWT 토큰을 입력 후 사용.
						
								---- 파일 처리 관련 ----
								1. FileResponse로 오는 경로는 /2025/3/25/b1c2d6192ab14fcaa284a0d5d34638de.jpg 이와 같은 리소스 형식임
								2. 프론트엔드에서 image 태그의 src 방식으로 사진을 조회하기 위해서는 버킷 경로 뒤에 붙여서 사용해야 함
						""");
		return new OpenAPI()
				.components(components)
				.info(info)
				.addSecurityItem(securityRequirement);
	}


}
