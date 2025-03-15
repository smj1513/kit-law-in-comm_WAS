package kit.se.capstone2.auth.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_USER,
	ROLE_LAWYER,
	ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}
