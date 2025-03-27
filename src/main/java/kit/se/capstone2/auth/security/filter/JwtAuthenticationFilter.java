package kit.se.capstone2.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final ObjectMapper objectMapper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(JwtProperties.AUTH_HEADER);

		if (authHeader == null) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = jwtUtils.extractToken(authHeader);
		try {
			jwtUtils.isExpired(token);
		} catch (ExpiredJwtException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getOutputStream().write(objectMapper.writeValueAsBytes(CommonResponse.error(ErrorCode.ACCESS_TOKEN_EXPIRED)));
			return;
		}

		if (!jwtUtils.isAccessToken(token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getOutputStream().write(objectMapper.writeValueAsBytes(CommonResponse.error(ErrorCode.INVALID_TOKEN)));
			return;
		}

		Role authorities = jwtUtils.getAuthorities(token);
		String username = jwtUtils.getUsername(token);
		Account account = Account.builder().role(authorities).username(username).build();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(account,
				null,
				account.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		filterChain.doFilter(request, response);
	}
	private PrintWriter getWriter(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return response.getWriter();
	}
}
