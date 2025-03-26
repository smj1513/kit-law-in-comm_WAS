package kit.se.capstone2.reports.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {

}
