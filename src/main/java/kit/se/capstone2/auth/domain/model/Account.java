package kit.se.capstone2.auth.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@SQLRestriction("approval_status != 'REJECTED'")
public class Account implements UserDetails {
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private Role role;

	@OneToOne
	private BaseUser user;

	//일반 사용자는 회원가입시 바로 승인, 변호사는 관리자 승인 후 승인
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(role);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
