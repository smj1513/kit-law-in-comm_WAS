package kit.se.capstone2.reports.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.common.entity.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class Report extends BaseTime {
	@Id
	@GeneratedValue
	private Long id;

	private String content;
}
