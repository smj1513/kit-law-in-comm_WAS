package kit.se.capstone2.reports.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.posts.answer.domain.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuestionReport extends Report{

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
}
