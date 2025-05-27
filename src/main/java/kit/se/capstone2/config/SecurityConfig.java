package kit.se.capstone2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import kit.se.capstone2.auth.security.filter.CustomLoginFilter;
import kit.se.capstone2.auth.security.filter.JwtAuthenticationFilter;
import kit.se.capstone2.auth.security.handler.CustomAuthenticationFailureHandler;
import kit.se.capstone2.auth.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.Message;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl.fromHierarchy;

//@EnableWebSecurity(debug = true)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final ObjectMapper objectMapper;
	private final JwtUtils jwtUtils;
	private final SecurityUtils securityUtils;
	private final AuthenticationConfiguration authConfig;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000", "http://202.31.202.38", "http://localhost:8080"));
		corsConfiguration.setAllowedMethods(List.of("*"));
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setExposedHeaders(List.of(
				JwtProperties.AUTH_HEADER
//				,
//				"Access-Control-Allow-Origin",
//				"Access-Control-Allow-Credentials",
//				"Access-Control-Expose-Headers",
//				"Access-Control-Max-Age",
//				"Content-Length",
//				"Cache-Control",
//				"Content-Type"
			));
		corsConfiguration.setMaxAge(3600L);
		corsConfiguration.setAllowCredentials(true);

		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return urlBasedCorsConfigurationSource;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(AbstractHttpConfigurer::disable);
		http.formLogin(AbstractHttpConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(jwtAuthenticationEntryPoint()));

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.requestMatchers(
						"/", "/swagger-ui/**", "/api-docs/**", "/common/**", "/ws-stomp/**" // 웹 소켓을 통한 인증 관리는 별도의 Interceptor를 통해 처리함.
				).permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/token/refresh").permitAll()
				.requestMatchers(HttpMethod.GET, "/users/info/**").permitAll()
				.requestMatchers("/users/join/**").permitAll()
				.requestMatchers("/users/legal-speciality").permitAll()
				.requestMatchers(HttpMethod.GET,"/question/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/questions/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/answers/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/reports/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
				.requestMatchers("/users/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
				.requestMatchers("/question/**").hasAuthority(Role.ROLE_LAWYER.name())
				.requestMatchers("/answers/**").hasAuthority(Role.ROLE_LAWYER.name())
				.anyRequest()
				.authenticated()
		);

		http.headers(header -> header
				.crossOriginResourcePolicy(corp -> corp
						.policy(CrossOriginResourcePolicyHeaderWriter.CrossOriginResourcePolicy.CROSS_ORIGIN)
				)
		);

		CustomLoginFilter customLoginFilter = new CustomLoginFilter(objectMapper, authenticationManager());
		customLoginFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(jwtUtils, objectMapper));
		customLoginFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(objectMapper));
		customLoginFilter.setFilterProcessesUrl("/login");

		http.addFilterAt(customLoginFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), CustomLoginFilter.class);

		return http.build();
	}
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring()
//				.requestMatchers("/users/join/**")
//				.requestMatchers("/users/legal-speciality")
//				.requestMatchers(HttpMethod.GET, "/questions/**")
//				.requestMatchers(HttpMethod.GET, "/answers/**")
//				.requestMatchers("/auth/token/refresh"); // 필터를 타지 않을 경로 지정
//	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		return fromHierarchy("""
				ROLE_ADMIN > ROLE_LAWYER
				ROLE_LAWYER > ROLE_USER
				""");
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(objectMapper);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {

		return new JwtAuthenticationFilter(jwtUtils, objectMapper, securityUtils);
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
