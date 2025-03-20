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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final ObjectMapper objectMapper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader(JwtProperties.AUTH_HEADER);
		if(authHeader == null){
			filterChain.doFilter(request, response);
			return;
		}
		PrintWriter writer = getWriter(response);

		String token = jwtUtils.extractToken(authHeader);
		try {
			jwtUtils.isExpired(token);
		} catch (ExpiredJwtException e){
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			writer.print(objectMapper.writeValueAsString(CommonResponse.error(ErrorCode.ACCESS_TOKEN_EXPIRED)));
			return;
		}
		if(!jwtUtils.isAccessToken(token)){
			writer.print(objectMapper.writeValueAsString(CommonResponse.error(ErrorCode.INVALID_TOKEN)));
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}

		Role authorities = jwtUtils.getAuthorities(token);
		String username = jwtUtils.getUsername(token);
		Account account = Account.builder().role(authorities).username(username).id(Long.parseLong(username)).build();

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(account, UUID.randomUUID().toString(), account.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		filterChain.doFilter(request, response);
	}
	private PrintWriter getWriter(HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		return response.getWriter();
	}
}
