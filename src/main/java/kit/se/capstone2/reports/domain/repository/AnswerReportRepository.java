package kit.se.capstone2.reports.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import kit.se.capstone2.user.domain.model.BaseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {

	@Query("select report from AnswerReport report where report.answer.id = :answerId")
	Page<AnswerReport> findByAnswerId(Long answerId, Pageable pageable);


	boolean existsByAnswerAndReporter(Answer answer, BaseUser reporter);

	@Modifying
	@Query("DELETE FROM AnswerReport r where r.answer.id in :answerIds")
	int deleteAllByAnswerIds(@Param("answerIds") List<Long> answerId);
}
