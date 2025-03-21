package kit.se.capstone2.user.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.posts.question.domain.model.Question;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Question> questions = new ArrayList<>();

	public void addAccount(Account account){
		this.account = account;
		account.setUser(this);
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
	}
}
