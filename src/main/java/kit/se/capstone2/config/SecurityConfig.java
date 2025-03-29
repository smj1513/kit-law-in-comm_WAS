package kit.se.capstone2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import kit.se.capstone2.auth.security.filter.CustomLoginFilter;
import kit.se.capstone2.auth.security.filter.JwtAuthenticationFilter;
import kit.se.capstone2.auth.security.handler.CustomAuthenticationFailureHandler;
import kit.se.capstone2.auth.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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

@EnableWebSecurity(debug = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final ObjectMapper objectMapper;
	private final JwtUtils jwtUtils;
	private final AuthenticationConfiguration authConfig;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000", "http://202.31.202.38"));
		corsConfiguration.setAllowedMethods(List.of("*"));
		corsConfiguration.setAllowedHeaders(List.of("*"));
		corsConfiguration.setExposedHeaders(List.of(
				"Authorization",
				"Authorization-refresh",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials",
				"Access-Control-Expose-Headers",
				"Access-Control-Max-Age",
				"Content-Length",
				"Cache-Control",
				"Content-Type"));
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
						"/", "/api",
						"/api/swagger-ui/**", "/swagger-ui/**",
						"/api/api-docs/**", "/api-docs/**",
						"/auth/**", "/api/common/**"
				).permitAll()
				.requestMatchers(HttpMethod.GET, "/api/questions/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/answers/**").permitAll()
				.requestMatchers("/api/users/admin/**").hasAuthority(Role.ROLE_ADMIN.name())
				.requestMatchers("/api/question/**").hasAuthority(Role.ROLE_LAWYER.name())
				.requestMatchers("/api/answers/**").hasAuthority(Role.ROLE_LAWYER.name())
				.anyRequest()
				.permitAll()
		);

		http.headers(header -> header
				.addHeaderWriter(
						(req, res) ->

								res.setHeader("Connection", "close")
				)
				.cacheControl(HeadersConfigurer.CacheControlConfig::disable)
				.crossOriginResourcePolicy(corp-> corp
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
		return new JwtAuthenticationFilter(jwtUtils, objectMapper);
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
