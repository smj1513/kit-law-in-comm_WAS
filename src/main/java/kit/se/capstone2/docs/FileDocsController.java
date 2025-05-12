package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.file.utils.ImageType;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일 처리 관련 API", description = "파일 관련 API")
public interface FileDocsController {


	@Operation(summary = "파일 업로드 API", description = "프로필 파일을 업로드한다.")
	@ApiResponse(responseCode = "201", description = "성공")
	CommonResponse<FileResponse> uploadFile(MultipartFile file, ImageType imageType);

	@Operation(summary = "파일 삭제 API", description = "파일을 삭제한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<FileResponse> deleteImageFile(Long id);
}
