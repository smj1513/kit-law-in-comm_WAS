package kit.se.capstone2.file.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.domain.repository.FilePropertyRepository;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.file.utils.factory.FilePropertyProvider;
import kit.se.capstone2.file.utils.FileStorage;
import kit.se.capstone2.file.utils.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FileAppService {
	private final FilePropertyRepository filePropertyRepository;
	private final SecurityUtils securityUtils;
	private final FileStorage fileStorage;
	private final FilePropertyProvider filePropertyProvider;

	public FileResponse uploadProfile(MultipartFile file) {
		return uploadFile(file, ImageType.PROFILE);
	}

	public FileResponse uploadFile(MultipartFile file, ImageType imageType) {
		Account currentUser = securityUtils.getCurrentUserAccount();

		FileProperty fileProperty = filePropertyProvider.create(file.getOriginalFilename(), file.getContentType(), file.getSize(), currentUser.getUser(), imageType);

		fileStorage.store(file, fileProperty.getPath());
		filePropertyRepository.save(fileProperty);
		return FileResponse.from(fileProperty);
	}

	public FileResponse deleteFile(Long filePropertyId) {
		FileProperty fileProperty = filePropertyRepository.findById(filePropertyId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "파일을 찾을 수 없습니다."));
		String path = fileProperty.getPath();

		fileStorage.delete(path);
		//연관 관계를 해제 시켜줘야 fileProperty가 삭제됨
		fileProperty.clear();
		filePropertyRepository.delete(fileProperty);
		filePropertyRepository.flush();

		return FileResponse.from(fileProperty);
	}
}
