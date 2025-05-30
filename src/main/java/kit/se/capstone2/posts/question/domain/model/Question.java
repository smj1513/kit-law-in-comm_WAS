package kit.se.capstone2.posts.question.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kit.se.capstone2.common.entity.BaseTime;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.ClientUser;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	private LocalDate firstOccurrenceDate;

	private Boolean isAnonymous;

	private int viewCount;


	@Enumerated(EnumType.STRING)
	private LegalSpeciality legalSpeciality;

	@ManyToOne
	@JoinColumn(name = "base_user_id")
	@NotNull
	private BaseUser author;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<QuestionReport> reports = new ArrayList<>();

	@Formula("(select count(*) from question_report qr where qr.question_id = id)")
	private int reportsCount;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<Answer> answers = new ArrayList<>();

	@PreRemove
	public void clear(){
		reports.clear();
		answers.clear();
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
		answer.setQuestion(this);
	}

	public void addAuthor(ClientUser author) {
		this.author = author;
		author.addQuestion(this);
	}

	public void addViewCount() {
		this.viewCount++;
	}

	public void addReport(QuestionReport report) {
		this.reports.add(report);
		report.setQuestion(this);
	}
}
