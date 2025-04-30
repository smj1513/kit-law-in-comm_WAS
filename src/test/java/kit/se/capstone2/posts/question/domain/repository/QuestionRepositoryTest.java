package kit.se.capstone2.posts.question.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

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

	@Test
	void searchKeywordAndCategory() {
		// 준비: 사용자 및 질문 데이터 저장
		ClientUser author = ClientUser.builder().name("name").nickname("nickname").birthDate(LocalDate.now()).build();
		clientUserRepository.saveAndFlush(author);

		Question q1 = Question.builder().title("IT_IP_TEST").content("IP_IT")
				.legalSpeciality(LegalSpeciality.IT_IP_FINANCE).author(author).build();
		Question q2 = Question.builder().title("기업테스트").content("기업")
				.legalSpeciality(LegalSpeciality.CORPORATE_LAW).author(author).build();
		Question q3 = Question.builder().title("IP").content("IP")
				.legalSpeciality(LegalSpeciality.IT_IP_FINANCE).author(author).build();
		Question q4 = Question.builder().title("이혼").content("이혼")
				.legalSpeciality(LegalSpeciality.FAMILY_DIVORCE).author(author).build();

		questionRepository.saveAndFlush(q1);
		questionRepository.saveAndFlush(q2);
		questionRepository.saveAndFlush(q3);
		questionRepository.saveAndFlush(q4);

		// 1. 키워드 "IT" + 분야 IT_IP_FINANCE (부분일치)
		String keyword = "%IT%";
		Page<Question> result1 = questionRepository.findByKeywordAndLegalSpeciality(keyword, LegalSpeciality.IT_IP_FINANCE, PageRequest.of(0, 10));
		assertEquals(1, result1.getTotalElements());
		assertEquals("IT_IP_TEST", result1.getContent().get(0).getTitle());

		// 2. 키워드 "기업" + 분야 CORPORATE_LAW (부분일치)
		String keyword2 = "%기업%";
		Page<Question> result2 = questionRepository.findByKeywordAndLegalSpeciality(keyword2, LegalSpeciality.CORPORATE_LAW, PageRequest.of(0, 10));
		assertEquals(1, result2.getTotalElements());
		assertEquals("기업테스트", result2.getContent().get(0).getTitle());

		// 3. 키워드 null + 분야 IT_IP_FINANCE (분야만 검색)
		Page<Question> result3 = questionRepository.findByKeywordAndLegalSpeciality(null, LegalSpeciality.IT_IP_FINANCE, PageRequest.of(0, 10));
		assertEquals(2, result3.getTotalElements()); // q1, q3

		// 4. 키워드 "IP" + 분야 null (키워드만 검색)
		String keyword3 = "%IP%";
		Page<Question> result4 = questionRepository.findByKeywordAndLegalSpeciality(keyword3, null, PageRequest.of(0, 10));
		assertEquals(2, result4.getTotalElements()); // q1, q3

		// 5. 키워드 null + 분야 null (전체 검색)
		Page<Question> result5 = questionRepository.findByKeywordAndLegalSpeciality(null, null, PageRequest.of(0, 10));
		assertEquals(4, result5.getTotalElements());
	}

}