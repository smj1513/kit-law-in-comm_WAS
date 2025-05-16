package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.answer.domain.repository.AnswerRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import kit.se.capstone2.user.interfaces.request.AdminRequest;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminAppServiceTest {

	@InjectMocks
	private AdminAppService adminAppService;

	@Mock
	private LawyerRepository lawyerRepository;

	@Mock
	private AnswerRepository answerRepository;
	@Mock
	private QuestionRepository questionRepository;

	public AdminAppServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getConfirmationLawyers() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Lawyer lawyer = new Lawyer();
		lawyer.setId(1L);
		lawyer.setName("Test Lawyer");
		lawyer.setAccount(new Account());
		when(lawyerRepository.findAllByApprovalStatus(ApprovalStatus.WAITING, pageRequest))
				.thenReturn(new PageImpl<>(List.of(lawyer)));

		Page<AdminResponse.ConfirmationLawyer> result = adminAppService.getConfirmationLawyers(0, 10);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals("Test Lawyer", result.getContent().get(0).getName());
	}

	@Test
	void confirmLawyer() {
		Lawyer lawyer = new Lawyer();
		lawyer.setId(1L);
		lawyer.setName("Test Lawyer");
		Account account = new Account();
		lawyer.setAccount(account);

		AdminRequest.Confirmation request = new AdminRequest.Confirmation();
		request.setIsApprove(true);

		when(lawyerRepository.findById(1L)).thenReturn(Optional.of(lawyer));

		AdminResponse.ConfirmationLawyer result = adminAppService.confirmLawyer(1L, request);

		assertNotNull(result);
		assertEquals(ApprovalStatus.APPROVED, account.getApprovalStatus());
		verify(lawyerRepository, times(1)).findById(1L);
	}


	@Test
	void removeQuestions() {
		AdminRequest.RemoveQuestionsReq request = new AdminRequest.RemoveQuestionsReq();
		request.setQuestionIds(List.of(1L, 2L));

		when(questionRepository.deleteAllById(request.getQuestionIds())).thenReturn(2);

		AdminResponse.RemoveQuestions result = adminAppService.removeQuestions(request);

		assertNotNull(result);
		assertEquals(2, result.getRemovedCount());
	}

	@Test
	void removeAnswers() {
		AdminRequest.RemoveAnswersReq request = new AdminRequest.RemoveAnswersReq();
		request.setAnswerIds(List.of(1L, 2L));

		when(answerRepository.deleteAllById(request.getAnswerIds())).thenReturn(2);

		AdminResponse.RemoveAnswers result = adminAppService.removeAnswers(request);

		assertNotNull(result);
		assertEquals(2, result.getRemovedCount());
	}
}