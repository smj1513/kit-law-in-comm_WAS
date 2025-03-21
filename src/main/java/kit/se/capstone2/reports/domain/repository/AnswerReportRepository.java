package kit.se.capstone2.reports.domain.repository;

import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {

	@Query("SELECT COUNT(ar) FROM AnswerReport ar WHERE ar.answer = :answer")
	int countByAnswerId(Answer answer);
}
