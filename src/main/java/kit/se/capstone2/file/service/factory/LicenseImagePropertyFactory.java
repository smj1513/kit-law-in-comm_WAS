package kit.se.capstone2.file.service.factory;

import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.domain.model.LicenseImageProperty;
import kit.se.capstone2.file.utils.ImageType;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import org.springframework.stereotype.Component;

@Component
public class LicenseImagePropertyFactory implements FilePropertyFactory {
	@Override
	public FileProperty create(String originFileName, String saveFileName, String path, String contentType, Long size, BaseUser uploader, ImageType type) {
		return LicenseImageProperty
				.builder()
				.lawyer((Lawyer) uploader)
				.originFileName(originFileName)
				.savedFileName(saveFileName)
				.path(path)
				.size(size)
				.contentType(contentType)
				.build();
	}

	@Override
	public boolean supports(ImageType imageType) {
		return ImageType.LICENSE.equals(imageType);
	}
}
