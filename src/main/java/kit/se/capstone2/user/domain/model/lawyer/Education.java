package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
	@Id
	@GeneratedValue
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "lawyer_id")
	private Lawyer lawyer;
}
