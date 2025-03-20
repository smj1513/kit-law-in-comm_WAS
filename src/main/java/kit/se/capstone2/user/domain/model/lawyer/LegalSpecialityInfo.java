package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LegalSpecialityInfo {
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private LegalSpeciality legalSpeciality;

	@ManyToOne
	@JoinColumn(name = "lawyer_id")
	private Lawyer lawyer;
}
