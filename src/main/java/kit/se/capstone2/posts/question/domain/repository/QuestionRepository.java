package kit.se.capstone2.posts.question.domain.repository;

import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {


	Page<Question> findByLegalSpeciality(LegalSpeciality legalSpeciality, Pageable pageable);
}
