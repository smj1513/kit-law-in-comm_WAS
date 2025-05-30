package kit.se.capstone2.auth.security;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.CustomAuthorizationException;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

	private final AccountRepository accountRepository;
	private final LawyerRepository lawyerRepository;
	private final ClientUserRepository clientUserRepository;
	private final JwtUtils jwtUtils;

	public Optional<Account> getNullableCurrentAccount(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal().equals("anonymousUser")){
			return Optional.empty();
		}
		Account principal = (Account) authentication.getPrincipal();
		return Optional.ofNullable(accountRepository.findByUsername(principal.getUsername()));
	}


	@NonNull
	public Account getCurrentUserAccount(){
		Account principal = (Account) Optional.ofNullable( SecurityContextHolder.getContext()
						.getAuthentication()).orElseThrow(() -> new CustomAuthorizationException(ErrorCode.NOT_FOUND_USER, "사용자 정보가 존재하지 않습니다.")).getPrincipal();

		return Optional.ofNullable(accountRepository.findByUsername(principal.getUsername()))
				.orElseThrow(()-> new CustomAuthorizationException(ErrorCode.NOT_FOUND_USER, "사용자 정보가 존재하지 않습니다."));
	}

	public Lawyer getCurrentLawyer() {
		Account account = getCurrentUserAccount();
		if(account.getRole().equals(Role.ROLE_LAWYER)){
			return lawyerRepository.findByAccount(account);
		}else{
			throw new CustomAuthorizationException(ErrorCode.NO_PERMISSION, "변호사가 아닙니다.");
		}
	}

	public ClientUser getCurrentClientUser() {
		Account account = getCurrentUserAccount();
		if(account.getRole().equals(Role.ROLE_USER) || account.getRole().equals(Role.ROLE_ADMIN)){
			return clientUserRepository.findByAccount(account);
		}else{
			throw new CustomAuthorizationException(ErrorCode.NO_PERMISSION, "일반 사용자가 아닙니다.");
		}
	}

	public Authentication setAuthentication(String jwt){

		Role authorities = jwtUtils.getAuthorities(jwt);
		String username = jwtUtils.getUsername(jwt);
		Account account = Account.builder().role(authorities).username(username).build();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(account,
				null,
				account.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		return usernamePasswordAuthenticationToken;
	}

	public BaseUser getBaseUser(Principal principal){
		String name = principal.getName();
		Account account = accountRepository.findByUsername(name);
		BaseUser user = account.getUser();
		return user;
	}
}
