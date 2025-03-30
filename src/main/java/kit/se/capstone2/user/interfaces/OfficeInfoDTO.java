package kit.se.capstone2.user.interfaces;

import kit.se.capstone2.user.domain.model.lawyer.OfficeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeInfoDTO {
	private String officeName;
	private String officeAddress;
	private String officePhoneNumber;

	public static OfficeInfoDTO from(OfficeInfo officeInfo){
		return OfficeInfoDTO.builder()
				.officeName(officeInfo.getName())
				.officeAddress(officeInfo.getAddress())
				.officePhoneNumber(officeInfo.getPhoneNumber())
				.build();
	}

	public OfficeInfo toEntity(){
		return OfficeInfo.builder()
				.name(officeName)
				.address(officeAddress)
				.phoneNumber(officePhoneNumber)
				.build();
	}
}
