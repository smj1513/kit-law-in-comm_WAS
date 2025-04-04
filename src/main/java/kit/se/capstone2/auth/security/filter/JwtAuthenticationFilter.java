package kit.se.capstone2.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final ObjectMapper objectMapper;
	private final SecurityUtils securityUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(JwtProperties.AUTH_HEADER);
		if (CorsUtils.isPreFlightRequest(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		if (authHeader == null) {
			SecurityContextHolder.clearContext();  // 중요: 기존 인증 정보 제거
			filterChain.doFilter(request, response);
			return;
		}
		String token = jwtUtils.extractToken(authHeader);
		try {
			jwtUtils.isExpired(token);
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(objectMapper.writeValueAsBytes(CommonResponse.error(ErrorCode.ACCESS_TOKEN_EXPIRED)));
			outputStream.close();
			return;
		}

		if (!jwtUtils.isAccessToken(token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(objectMapper.writeValueAsBytes(CommonResponse.error(ErrorCode.INVALID_TOKEN)));
			outputStream.close();
			return;
		}
		securityUtils.setAuthentication(token);
		filterChain.doFilter(request, response);
//
//		// 응답 헤더 로깅
//		Collection<String> headerNames = response.getHeaderNames();
//		for (String headerName : headerNames) {
//			String headerValue = response.getHeader(headerName);
//			log.info("Response Header: {} = {}", headerName, headerValue);
//		}
	}
}
