package kit.se.capstone2.user.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.auth.domain.model.Account;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class BaseUser {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String phoneNumber;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Account account;

	public void addAccount(Account account){
		this.account = account;
		account.setUser(this);
	}

}
