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
			    SELECT new kit.se.capstone2.posts.question.interfaces.response.QuestionResponse$PostQuestion(q, COUNT(r))
			    FROM Question q
			    LEFT JOIN fetch q.reports r
			    WHERE q.legalSpeciality = :legalSpeciality
			    GROUP BY q.id
			""",
			countQuery = """
					    SELECT COUNT(DISTINCT q.id)
					    FROM Question q
					    WHERE q.legalSpeciality = :legalSpeciality
					""")
	Page<QuestionResponse.PostQuestion> findByLegalSpeciality(@Param("legalSpeciality") LegalSpeciality legalSpeciality, Pageable pageable);

	@Query(value = """
			select new kit.se.capstone2.posts.question.interfaces.response.QuestionResponse$PostQuestion(q, count(r))
			from Question q left join fetch q.reports r GROUP BY q.id
			"""
			, countQuery = """
			select count(distinct q) from Question q
			"""
	)
	Page<QuestionResponse.PostQuestion> findAllWithReportsCount(Pageable pageable);

	@Query(value = """
			select new kit.se.capstone2.posts.question.interfaces.response.QuestionResponse$PostQuestion(q, count(r))
			from Question q left join fetch q.reports r where q.title like :keyword or q.content like :keyword GROUP BY q.id
			""", countQuery = """
			select count(distinct q) from Question q where q.title like :keyword or q.content like :keyword
			""")
	Page<QuestionResponse.PostQuestion> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


}
