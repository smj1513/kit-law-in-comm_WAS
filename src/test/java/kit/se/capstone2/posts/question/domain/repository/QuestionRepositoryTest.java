package kit.se.capstone2.posts.question.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class QuestionRepositoryTest {

	@Autowired QuestionRepository questionRepository;

	@Autowired
	QuestionReportRepository reportRepository;

	@Autowired @PersistenceContext
	EntityManager em;

	@Autowired
	ClientUserRepository clientUserRepository;
	@Autowired
	AccountRepository accountRepository;


	@Test
	void findAllWithReportCount(){
		ClientUser build = ClientUser.builder().name("name").nickname("nickname").birthDate(LocalDate.now()).build();
		clientUserRepository.saveAndFlush(build);
		em.refresh(build);
		Question q = Question.builder()
				.title("title")
				.content("content")
				.legalSpeciality(LegalSpeciality.IT_IP_FINANCE)
				.build();
		q.addAuthor(build);
		questionRepository.saveAndFlush(q);
		for (int i = 0; i < 100 ; i++){
			QuestionReport report = QuestionReport.builder().reason("test").build();
			q.addReport(report);
			build.addReport(report);
			reportRepository.saveAndFlush(report);
		}
		em.flush();
		em.clear(); // 영속성 컨텍스트 초기화
		Question first = questionRepository.findAll(PageRequest.of(0, 10)).getContent().getFirst();
		assertEquals(100, first.getReportsCount());
	}

	@Test
	void findByLegalSpeciality() {
		// given
		ClientUser build = ClientUser.builder().name("name").nickname("nickname").birthDate(LocalDate.now()).build();
		clientUserRepository.saveAndFlush(build);
		em.refresh(build);
		Question q = Question.builder()
				.title("title")
				.content("content")
				.legalSpeciality(LegalSpeciality.IT_IP_FINANCE)
				.build();
		q.addAuthor(build);
		Question saved =
				Question.builder()
						.title("2title")
						.content("2content")
						.legalSpeciality(LegalSpeciality.ASSAULT_DEFAMATION)
						.build();
		saved.addAuthor(build);
		questionRepository.saveAndFlush(q);
		questionRepository.saveAndFlush(saved);
		// when
		Page<Question> page = questionRepository.findByLegalSpeciality(LegalSpeciality.IT_IP_FINANCE, PageRequest.of(0, 10));
		Question question = questionRepository.findByLegalSpeciality(LegalSpeciality.IT_IP_FINANCE, PageRequest.of(0, 10)).getContent().getFirst();
		// then
		assertEquals(q.getTitle(), question.getTitle());
		assertEquals(page.getTotalElements(), 1);
	}
}