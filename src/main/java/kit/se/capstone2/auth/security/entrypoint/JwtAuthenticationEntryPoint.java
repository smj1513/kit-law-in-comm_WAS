package kit.se.capstone2.auth.security.entrypoint;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;


@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getOutputStream().print(objectMapper.writeValueAsString(CommonResponse.error(ErrorCode.LOGIN_FAILED)));
	}

}
