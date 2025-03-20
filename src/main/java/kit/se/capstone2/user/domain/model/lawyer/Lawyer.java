package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
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

	private String phoneNumber;

	private String description;

	@OneToOne(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private OfficeInfo officeInfo;

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<LegalSpecialityInfo> legalSpecialties = new ArrayList<>();

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Career> careers = new ArrayList<>();

	@OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<Education> educations = new ArrayList<>();
}
