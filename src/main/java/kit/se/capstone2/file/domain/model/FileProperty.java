package kit.se.capstone2.file.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.common.entity.BaseTime;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FileProperty extends BaseTime {

	@Id
	@GeneratedValue
	private Long id;
	private String originFileName;
	private String savedFileName;
	private String contentType;
	private Long size;
	private String path;

	@ManyToOne
	private BaseUser uploader;
}
