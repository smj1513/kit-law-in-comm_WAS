package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
