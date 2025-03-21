package kit.se.capstone2.reports.domain.repository;

import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long> {
	@Query("SELECT COUNT(qr) FROM QuestionReport qr WHERE qr.question = :question")
	int countByQuestion(Question question);
}
