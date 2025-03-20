package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeInfo {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String address;
	private String phoneNumber;

	@OneToOne
	private Lawyer lawyer;
}
