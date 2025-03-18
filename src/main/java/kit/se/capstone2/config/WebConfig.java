package kit.se.capstone2.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
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
				.allowedOriginPatterns("*")
				.allowedMethods("GET","POST", "PUT", "DELETE")
				.allowCredentials(true);
	}
}
