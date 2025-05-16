package kit.se.capstone2.reports.domain.repository;

import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.user.domain.model.BaseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface QuestionReportRepository extends JpaRepository<QuestionReport, Long> {

	boolean existsByQuestionAndReporter(Question question, BaseUser reporter);

	@Query("SELECT qr FROM QuestionReport qr WHERE qr.question.id = :questionId")
	Page<QuestionReport> findByQuestionId(Long questionId, Pageable pageRequest);

	@Modifying
	@Query("DELETE FROM QuestionReport qr WHERE qr.question.id in :questionIds")
	int deleteAllByQuestionIds(@Param("questionIds") List<Long> questionIds);
}
