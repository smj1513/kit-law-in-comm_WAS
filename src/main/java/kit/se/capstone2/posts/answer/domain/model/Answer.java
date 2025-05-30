package kit.se.capstone2.posts.answer.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.common.entity.BaseTime;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Answer extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(columnDefinition = "TEXT")
	private String content;


	@ManyToOne
	@JoinColumn(name = "author_id")
	private Lawyer author;


	@ManyToOne
	@JoinColumn(name = "question_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Question question;

	@OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<AnswerReport> answerReports = new ArrayList<>();

	@Formula("(select count(*) from answer_report ar where ar.answer_id = id)")
	private int reportsCount;

	public void addReport(AnswerReport answerReport) {
		this.answerReports.add(answerReport);
		answerReport.setAnswer(this);
	}

	@PreRemove
	public void clear(){
		answerReports.clear();
	}
}
