package kit.se.capstone2.file.utils.factory;

import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.utils.ImageType;
import kit.se.capstone2.user.domain.model.BaseUser;

import java.lang.reflect.Member;

public interface FilePropertyFactory {
	FileProperty create(String originFileName, String saveFileName, String path, String contentType, Long size, BaseUser uploader, ImageType type);

	boolean supports(ImageType imageType);
}
