package kit.se.capstone2.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.interfaces.response.LoginResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtUtils jwtUtils;
	private final ObjectMapper om;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ServletOutputStream writer = response.getOutputStream();
		LoginResponse loginResponse = jwtUtils.generateToken(authentication);
		String body = om.writeValueAsString(CommonResponse.success(SuccessCode.OK, loginResponse));
		writer.print(body);
		writer.flush();
		writer.close();
	}
}
