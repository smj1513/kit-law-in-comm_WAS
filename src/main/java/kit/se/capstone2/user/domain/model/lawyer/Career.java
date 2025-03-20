package kit.se.capstone2.user.domain.model.lawyer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Career {
	@Id
	@GeneratedValue
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn(name = "lawyer_id")
	private Lawyer lawyer;
}
