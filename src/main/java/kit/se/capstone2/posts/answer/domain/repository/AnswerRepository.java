package kit.se.capstone2.posts.answer.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Query("SELECT a FROM Answer a WHERE a.question.id = :questionId")
	Page<Answer> findByQuestionId(@Param("questionId") Long questionId, Pageable pageable);

	@Query("SELECT a FROM Answer a WHERE a.reportsCount >= :reportsCount")
	Page<Answer> findByReportsCount(int reportsCount, Pageable pageable);

	@Query("SELECT a FROM Answer a WHERE a.author.id = :id")
	Page<Answer> findByAuthorId(Long id, Pageable pageable);

	@Modifying
	@Query("DELETE FROM Answer a WHERE a.id in :ids")
	int deleteAllById(List<Long> ids);
}
