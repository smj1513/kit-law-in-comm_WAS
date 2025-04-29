package kit.se.capstone2.file.utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class AmazonS3FileStorage implements FileStorage{

	private final AmazonS3 s3;
	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Override
	public void store(MultipartFile file, String path) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			metadata.setContentType(file.getContentType());
			s3.putObject(bucketName, path, file.getInputStream(), metadata);
		} catch (Exception e) {
			throw new BusinessLogicException(ErrorCode.FILE_PROCESS_FAILURE, "파일 저장에 실패했습니다.");
		}

	}

	@Override
	public void delete(String path) {
		try {
			s3.deleteObject(bucketName, path);
		} catch (Exception e) {
			throw new BusinessLogicException(ErrorCode.FILE_PROCESS_FAILURE, "파일 삭제에 실패했습니다.");
		}
	}
}
