package kit.se.capstone2.auth;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.CustomAuthorizationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

	private final AccountRepository accountRepository;

	@NonNull
	public Account getCurrentUser(){
		Account principal = Optional.of((Account) SecurityContextHolder.getContext()
						.getAuthentication()
						.getPrincipal())
				.orElseThrow(() -> new CustomAuthorizationException(ErrorCode.NOT_FOUND_USER, "사용자 정보가 존재하지 않습니다."));

		return Optional.of(accountRepository.findById(principal.getId())).orElseThrow(()-> new CustomAuthorizationException(ErrorCode.NOT_FOUND_USER, "사용자 정보가 존재하지 않습니다."));
	}
}
