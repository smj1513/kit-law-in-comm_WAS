package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import kit.se.capstone2.file.domain.model.LicenseImageProperty;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("lawyer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Lawyer extends BaseUser {

	private String description;

	@OneToOne(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private OfficeInfo officeInfo;

	@OneToOne(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private LicenseImageProperty license;


	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Answer> answers = new ArrayList<>();

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<LegalSpecialityInfo> legalSpecialities = new ArrayList<>();

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<Career> careers = new ArrayList<>();

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<Education> educations = new ArrayList<>();

	public void addLicense(LicenseImageProperty license) {
		this.license = license;
		license.setLawyer(this);
	}

	public void addOfficeInfo(OfficeInfo officeInfo) {
		this.officeInfo = officeInfo;
		officeInfo.setLawyer(this);
	}

	public void addAnswer(Answer answer) {
		this.answers.add(answer);
		answer.setAuthor(this);
	}

	public void addSpeciality(LegalSpecialityInfo legalSpecialityInfo) {
		this.legalSpecialities.add(legalSpecialityInfo);
		legalSpecialityInfo.setLawyer(this);
	}

	public void updateSpeciality(List<LegalSpeciality> legalSpecialityInfos) {
		List<LegalSpecialityInfo> toRemove = this.legalSpecialities.stream()
				.filter(legalSpecialityInfo -> !legalSpecialityInfos.contains(legalSpecialityInfo.getLegalSpeciality()))
				.toList();
		List<LegalSpeciality> toAdd = legalSpecialityInfos.stream()
				.filter(newLegalSpeciality -> this.legalSpecialities.stream()
						.noneMatch(existingLegalSpeciality -> existingLegalSpeciality.getLegalSpeciality().equals(newLegalSpeciality)))
				.toList();
		toRemove.forEach(legalSpecialityInfo -> legalSpecialityInfo.setLawyer(null));
		//삭제
		this.legalSpecialities.removeAll(toRemove);
		//추가
		toAdd.forEach(newLegalSpeciality -> {
			LegalSpecialityInfo legalSpecialityInfo = new LegalSpecialityInfo();
			legalSpecialityInfo.setLawyer(this);
			legalSpecialityInfo.setLegalSpeciality(newLegalSpeciality);
			this.legalSpecialities.add(legalSpecialityInfo);
		});
	}

	public void updateCareers(List<String> careers) {
		// 기존 데이터와 새로운 데이터를 비교
		List<Career> toRemove = this.careers.stream()
				.filter(career -> !careers.contains(career.getContent()))
				.toList();

		List<String> toAdd = careers.stream()
				.filter(newCareer -> this.careers.stream()
						.noneMatch(existingCareer -> existingCareer.getContent().equals(newCareer)))
				.toList();

		// 삭제
		toRemove.forEach(career -> career.setLawyer(null));
		this.careers.removeAll(toRemove);

		// 추가
		toAdd.forEach(newCareer -> {
			Career career = new Career();
			career.setLawyer(this);
			career.setContent(newCareer);
			this.careers.add(career);
		});
	}

	public void updateEducations(List<String> educations) {
		List<Education> toRemove = this.educations.stream()
				.filter(edu -> !educations.contains(edu.getContent()))
				.toList();
		List<String> toAdd = educations.stream()
				.filter(newEdu -> this.educations.stream()
						.noneMatch(existingEdu -> existingEdu.getContent().equals(newEdu))
				)
				.toList();
		toRemove.forEach(education -> education.setLawyer(null));
		this.educations.removeAll(toRemove);

		toAdd.forEach(newEdu ->{
			Education education = new Education();
			education.setLawyer(this);
			education.setContent(newEdu);
			this.educations.add(education);
		});
	}

	public void updateOfficeInfo(OfficeInfo officeInfo){
		this.officeInfo.setAddress(officeInfo.getAddress());
		this.officeInfo.setName(officeInfo.getName());
		this.officeInfo.setPhoneNumber(officeInfo.getPhoneNumber());
	}

	@Override
	public String getNickname() {
		return this.getName();
	}

	@Override
	public String getResponseName(){
		return this.getName();
	}
}
