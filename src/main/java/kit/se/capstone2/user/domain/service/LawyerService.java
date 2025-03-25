package kit.se.capstone2.user.domain.service;

import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.file.application.FileAppService;
import kit.se.capstone2.file.domain.model.FileProperty;
import kit.se.capstone2.file.domain.model.LicenseImageProperty;
import kit.se.capstone2.file.service.factory.FilePropertyProvider;
import kit.se.capstone2.file.utils.FileStorage;
import kit.se.capstone2.file.utils.ImageType;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Transactional
public class LawyerService {
	private final FileStorage fileStorage;
	private final FilePropertyProvider filePropertyProvider;
	private final FileAppService fileAppService;

	public Lawyer createLawyerLicense(Lawyer lawyer, MultipartFile file) {

		FileProperty fileProperty = filePropertyProvider.create(file.getOriginalFilename(), file.getContentType(), file.getSize(), lawyer, ImageType.LICENSE);
//
//		File temp = null;
//		try {
//			temp = Files.createTempFile("temp",".tmp").toFile();
//			file.transferTo(temp);
//		}catch (Exception e){
//			throw new BusinessLogicException(ErrorCode.FILE_PROCESS_FAILURE, "파일 저장에 실패했습니다.");
//		}
		lawyer.addLicense((LicenseImageProperty) fileProperty);
		fileStorage.store(file, fileProperty.getPath());
		return lawyer;
	}
}
