package kit.se.capstone2.posts.answer.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.common.entity.BaseTime;
import kit.se.capstone2.posts.question.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.ClientUser;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseTime {
	@Id
	@GeneratedValue
	private Long id;

	private String title;

	private String content;

	private long viewCount;

	@Enumerated(EnumType.STRING)
	private LegalSpeciality legalSpeciality;

	@ManyToOne
	@JoinColumn(name = "client_user_id")
	private ClientUser clientUser;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<QuestionReport> reports = new ArrayList<>();

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Answer> answers = new ArrayList<>();
}
