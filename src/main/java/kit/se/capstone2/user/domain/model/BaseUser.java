package kit.se.capstone2.user.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.file.domain.model.ProfileImageProperty;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.reports.domain.model.Report;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;


	private LocalDate birthDate;

	@Column(unique = true)
	private String phoneNumber;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Account account;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ProfileImageProperty profileImage;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Question> questions = new ArrayList<>();

	@OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Report> reports = new ArrayList<>();

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<ChatRoom> chatRooms = new ArrayList<>();


	public void addAccount(Account account){
		this.account = account;
		account.setUser(this);
	}

	public void addReport(Report report){
		this.reports.add(report);
		report.setReporter(this);
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
		question.setAuthor(this);
	}

	public void addChatRoom(ChatRoom chatRoom) {
		this.chatRooms.add(chatRoom);
		chatRoom.setCreator(this);
	}

	abstract public String getNickname();

	public ChatRoom createChat(BaseUser baseUser){
		return ChatRoom.builder()
				.creator(this)
				.participant(baseUser)
				.build();
	}

	public boolean isAdmin() {
		return account.getRole().equals(Role.ROLE_ADMIN);
	}

	public boolean isLawyer() {
		return account.getRole().equals(Role.ROLE_LAWYER);
	}

	public boolean isClient() {
		return account.getRole().equals(Role.ROLE_USER);
	}
	public abstract String getResponseName();
}
