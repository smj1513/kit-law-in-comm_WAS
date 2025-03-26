package kit.se.capstone2.posts.question.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = """
			select q FROM Question q WHERE q.legalSpeciality = :legalSpeciality 
			""")
	Page<Question> findByLegalSpeciality(@Param("legalSpeciality") LegalSpeciality legalSpeciality, Pageable pageable);

	@Query(value = """
			select q from Question q where q.title like :keyword or q.content like :keyword
			""")
	Page<Question> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = """
			select q from Question q where q.reportsCount >= :reportsCount
			""")
	Page<Question> findByReportsCount(int reportsCount, Pageable pageable);
}
