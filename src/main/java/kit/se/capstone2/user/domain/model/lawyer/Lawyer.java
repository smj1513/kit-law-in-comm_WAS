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
		license.setUploader(this);
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
		this.legalSpecialities.clear();
		this.legalSpecialities.addAll(legalSpecialityInfos.stream().map(legalSpeciality -> {
			LegalSpecialityInfo info = LegalSpecialityInfo.builder().legalSpeciality(legalSpeciality).build();
			info.setLawyer(this);
			return info;
		}).toList());
	}

	public void updateCareers(List<String> careers) {
		this.careers.clear();
		this.careers.addAll(careers.stream().map(career -> {
			Career care = new Career();
			care.setLawyer(this);
			care.setContent(career);
			return care;
		}).toList());
	}

	public void updateEducations(List<String> educations) {
		this.educations.clear();
		this.educations.addAll(educations.stream().map(education -> {
			Education edu = new Education();
			edu.setLawyer(this);
			edu.setContent(education);
			return edu;
		}).toList());
	}

	@Override
	public String getNickname() {
		return this.getName();
	}
}
