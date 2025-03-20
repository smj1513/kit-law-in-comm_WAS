package kit.se.capstone2.user.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
	private String nickname;
	private LocalDate birthDate;
}
