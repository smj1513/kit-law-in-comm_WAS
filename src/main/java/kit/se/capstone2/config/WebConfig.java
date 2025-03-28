package kit.se.capstone2.config;
import jakarta.servlet.MultipartConfigElement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableAsync
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Override
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://202.31.202.38")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
				.allowedHeaders("Authorization", "Content-Type")
				.exposedHeaders("Authorization",
						"Access-Control-Allow-Origin",
						"Access-Control-Allow-Credentials")
				.maxAge(3600L)
				.allowCredentials(true);
	}
}
