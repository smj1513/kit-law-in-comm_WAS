package kit.se.capstone2.auth.domain.service;

import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.of(accountRepository.findByUsernameAndApprovalStatus(username, ApprovalStatus.APPROVED))
				.orElseThrow(() -> new BadCredentialsException("사용자 정보가 존재하지 않습니다."));
	}
}
