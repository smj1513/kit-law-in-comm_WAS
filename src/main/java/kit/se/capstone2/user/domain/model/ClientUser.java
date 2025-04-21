package kit.se.capstone2.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("client")
@Getter
@Setter
@SuperBuilder
public class ClientUser extends BaseUser {
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}", message = "닉네임은 2~8자의 한글 및 영문 대소문자와 숫자로 이루어져야 합니다.")
	private String nickname;

	@Override
	public String getResponseName() {
		return nickname;
	}
}
