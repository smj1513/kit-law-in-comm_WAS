package kit.se.capstone2.posts.answer.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kit.se.capstone2.posts.answer.application.AnswerAppService;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import kit.se.capstone2.reports.domain.repository.AnswerReportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class AnswerRepositoryTest {

	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	AnswerReportRepository answerReportRepository;
	@Autowired @PersistenceContext
	EntityManager em;

	@Test
	void findByQuestionId() {
	}

	@Test
	void findByReportsCount() {
		//given
		Answer answer = Answer.builder().content("content").build();
		answerRepository.saveAndFlush(answer);
		em.refresh(answer);

		for (int i = 0; i < 10; i++) {
			AnswerReport reason = AnswerReport.builder().reason("reason").build();
			answer.addReport(reason);
			answerReportRepository.saveAndFlush(reason);
		}
		//when
		Page<Answer> thres5 = answerRepository.findByReportsCount(5, PageRequest.of(0, 10));
		Page<Answer> thres11 = answerRepository.findByReportsCount(11, PageRequest.of(0, 10));
		//then
		assertEquals(1, thres5.getTotalElements());
		assertEquals(0, thres11.getTotalElements());

	}
	@Test
	void deleteAllById() {
		// given
		Answer answer1 = Answer.builder().content("Answer 1").build();
		Answer answer2 = Answer.builder().content("Answer 2").build();
		Answer answer3 = Answer.builder().content("Answer 3").build();

		answerRepository.saveAndFlush(answer1);
		answerRepository.saveAndFlush(answer2);
		answerRepository.saveAndFlush(answer3);

		em.flush();
		em.clear();

		// when
		int deletedCount = answerRepository.deleteAllById(List.of(answer1.getId(), answer2.getId()));
		em.flush();
		em.clear();

		// then
		assertEquals(2, deletedCount);
		assertTrue(answerRepository.findById(answer1.getId()).isEmpty());
		assertTrue(answerRepository.findById(answer2.getId()).isEmpty());
		assertTrue(answerRepository.findById(answer3.getId()).isPresent());
	}
}