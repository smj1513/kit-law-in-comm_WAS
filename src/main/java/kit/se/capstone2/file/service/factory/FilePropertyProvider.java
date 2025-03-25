package kit.se.capstone2.file.service.factory;

import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.utils.FilePathGenerator;
import kit.se.capstone2.file.utils.ImageType;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FilePropertyProvider {

	private final List<FilePropertyFactory> filePropertyFactories;
	private final FilePathGenerator filePathGenerator;

	@Autowired
	public FilePropertyProvider(LicenseImagePropertyFactory licenseImagePropertyFactory, ProfileImagePropertyFactory profileImagePropertyFactory, FilePathGenerator filePathGenerator){
		this.filePropertyFactories = List.of(licenseImagePropertyFactory, profileImagePropertyFactory);
		this.filePathGenerator = filePathGenerator;
	}

	public FileProperty create(String fileName, String contentType, Long size, BaseUser uploader, ImageType imageType) {
		String savedFile = filePathGenerator.generateSaveFileName(fileName);
		String path = filePathGenerator.generatePath(savedFile);

		for (FilePropertyFactory factory : filePropertyFactories) {
			if (factory.supports(imageType)) {
				return factory.create(fileName, savedFile, path, contentType, size, uploader, imageType);
			}
		}
		throw new BusinessLogicException(ErrorCode.FILE_TYPE_UNSUPPORTED, "지원하지 않는 파일 타입입니다.");
	}
}
