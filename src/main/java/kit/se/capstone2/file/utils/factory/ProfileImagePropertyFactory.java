package kit.se.capstone2.file.utils.factory;

import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.domain.model.ProfileImageProperty;
import kit.se.capstone2.file.utils.ImageType;
import kit.se.capstone2.user.domain.model.BaseUser;
import org.springframework.stereotype.Component;

@Component
public class ProfileImagePropertyFactory implements FilePropertyFactory{
	@Override
	public FileProperty create(String originFileName, String saveFileName, String path, String contentType, Long size,BaseUser uploader, ImageType type) {
		return ProfileImageProperty.builder()
				.user(uploader)
				.originFileName(originFileName)
				.savedFileName(saveFileName)
				.path(path)
				.size(size)
				.contentType(contentType)
				.build();
	}

	@Override
	public boolean supports(ImageType imageType) {
		return ImageType.PROFILE.equals(imageType);
	}
}
