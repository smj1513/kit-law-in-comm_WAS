package kit.se.capstone2.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;


@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private final ObjectMapper om;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().print(om.writeValueAsString(CommonResponse.error(ErrorCode.LOGIN_FAILED)));
	}
}
