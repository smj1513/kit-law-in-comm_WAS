package kit.se.capstone2.auth.security;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.CustomAuthorizationException;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
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

		return Optional.of(accountRepository.findByUsername(principal.getUsername()))
				.orElseThrow(()-> new CustomAuthorizationException(ErrorCode.NOT_FOUND_USER, "사용자 정보가 존재하지 않습니다."));
	}

	public Lawyer getCurrentLawyer() {
		Account currentUser = getCurrentUser();
		if(currentUser.getRole().equals(Role.ROLE_LAWYER)){
			return (Lawyer) currentUser.getUser();
		}else{
			throw new CustomAuthorizationException(ErrorCode.NO_PERMISSION, "변호사가 아닙니다.");
		}
	}
}
