package kit.se.capstone2.posts.question.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query(value = """
			select q FROM Question q WHERE q.legalSpeciality = :legalSpeciality 
			""")
	Page<Question> findByLegalSpeciality(@Param("legalSpeciality") LegalSpeciality legalSpeciality, Pageable pageable);

	@Query(value = """
			 select q from Question q where (:keyword is null or q.title like :keyword or q.content like :keyword)
             and (:legalSpeciality is null or q.legalSpeciality = :legalSpeciality)
			""")
	Page<Question> findByKeywordAndLegalSpeciality(@Param("keyword") String keyword, @Param("legalSpeciality") LegalSpeciality legalSpeciality, Pageable pageable);

	@Query(value = """
			select q from Question q where q.reportsCount >= :reportsCount
			""")
	Page<Question> findByReportsCount(int reportsCount, Pageable pageable);

	@Query(value = """
			select q from Question q where q.author.id = :authorId
			""")
	Page<Question> findByAuthorId(Long authorId, Pageable pageable);

	@Modifying
	@Query("DELETE FROM Question q where q.id in :ids")
	int deleteAllById(List<Long> ids);
}
