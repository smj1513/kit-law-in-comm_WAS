package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.answer.domain.repository.AnswerRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.interfaces.request.MyPageRequest;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageAppService {
	private final SecurityUtils securityUtils;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	public MyPageResponse.GeneralInfo getGeneralInfo() {
		BaseUser user = securityUtils.getCurrentUserAccount().getUser();
		return MyPageResponse.GeneralInfo.builder()
				.name(user.getName())
				.nickname(user.getNickname())
				.birth(user.getBirthDate())
				.build();
	}

	public MyPageResponse.LawyerInfo getLawyerInfo() {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		return MyPageResponse.LawyerInfo.from(currentLawyer);
	}

	public MyPageResponse.GeneralInfo updateGeneralInfo(MyPageRequest.UpdateGeneralInfo request) {
		ClientUser currentClientUser = securityUtils.getCurrentClientUser();
		currentClientUser.setNickname(request.getNickname());
		currentClientUser.setName(request.getName());
		currentClientUser.setBirthDate(request.getBirth());
		return MyPageResponse.GeneralInfo.builder()
				.name(currentClientUser.getName())
				.nickname(currentClientUser.getNickname())
				.birth(currentClientUser.getBirthDate())
				.build();
	}

	public MyPageResponse.LawyerInfo updateLawyerInfo(MyPageRequest.UpdateLawyerInfo request) {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		currentLawyer.setDescription(request.getDescription());
		currentLawyer.setPhoneNumber(request.getPhoneNumber());
		currentLawyer.updateCareers(request.getCareers());
		currentLawyer.updateEducations(request.getEducations());
		currentLawyer.updateSpeciality(request.getLegalSpecialities());
		currentLawyer.setOfficeInfo(request.getOfficeInfo().toEntity());
		return MyPageResponse.LawyerInfo.from(currentLawyer);
	}

	public Page<MyPageResponse.QuestionInfo> retrieveQuestions(int page, int size) {
		BaseUser user = securityUtils.getCurrentUserAccount().getUser();
		Page<Question> questions = questionRepository.findByAuthorId(user.getId(), PageRequest.of(page, size));
		return questions.map(MyPageResponse.QuestionInfo::from);
	}

	public Page<MyPageResponse.AnswerInfo> retrieveAnswers(int page, int size) {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		Page<Answer> answers = answerRepository.findByAuthorId(currentLawyer.getId(), PageRequest.of(page, size));
		return answers.map(MyPageResponse.AnswerInfo::from);
	}
}
