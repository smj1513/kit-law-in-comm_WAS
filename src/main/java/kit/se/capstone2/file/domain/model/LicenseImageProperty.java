package kit.se.capstone2.file.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LicenseImageProperty extends FileProperty {

	@OneToOne
	private Lawyer lawyer;

	@Override
	public void clear() {
		lawyer.setLicense(null);
		this.lawyer = null;
	}
}
