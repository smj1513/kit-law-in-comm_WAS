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
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final ObjectMapper objectMapper;
	private final JwtUtils jwtUtils;
	private final AuthenticationConfiguration authConfig;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(AbstractHttpConfigurer::disable);
		http.formLogin(AbstractHttpConfigurer::disable);
		http.logout(AbstractHttpConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
						.requestMatchers("/", "/api","/api/swagger-ui/**", "/swagger-ui/**","/api/api-docs/**","/api-docs/**", "/auth/**").permitAll()
						.requestMatchers("/users/**").permitAll()
						.requestMatchers("/users/admin/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
						.requestMatchers("/question/{question-id}/answers").hasAnyAuthority(Role.ROLE_LAWYER.name())
						.requestMatchers(new AntPathRequestMatcher("/questions/**", "GET")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/questions/**", "GET")).permitAll()
						.requestMatchers("/answers/**").hasAnyAuthority(Role.ROLE_LAWYER.name())
						.requestMatchers(new AntPathRequestMatcher("/answers/**", "GET")).permitAll()
						.anyRequest()
						.authenticated()
		);

		http.addFilterAt(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint()));

		return http.build();
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		return RoleHierarchyImpl.fromHierarchy(String.format("""
					%s > %s
					%s > %s
				""", Role.ROLE_ADMIN.name(), Role.ROLE_LAWYER.name(), Role.ROLE_LAWYER.name(), Role.ROLE_USER.name())
		);
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(objectMapper);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtUtils, objectMapper);
	}

	public CustomLoginFilter customLoginFilter() throws Exception {
		CustomLoginFilter customLoginFilter = new CustomLoginFilter(objectMapper, authenticationManager());
		customLoginFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(jwtUtils, objectMapper));
		customLoginFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(objectMapper));
		customLoginFilter.setFilterProcessesUrl("/login");
		return customLoginFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() { //cors 정책 설정 실 운영 들어가기전에 변경해야됨
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOriginPatterns(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

		return urlBasedCorsConfigurationSource;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
