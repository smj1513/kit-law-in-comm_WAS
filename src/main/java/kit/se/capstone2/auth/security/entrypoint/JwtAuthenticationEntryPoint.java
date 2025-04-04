package kit.se.capstone2.auth.security.entrypoint;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;


@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		if (authException instanceof BadCredentialsException) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(objectMapper.writeValueAsString(CommonResponse.error(ErrorCode.INVALID_TOKEN)));
			outputStream.close();
		}
	}
}
