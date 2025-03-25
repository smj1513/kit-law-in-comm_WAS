package kit.se.capstone2.file.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.FileDocsController;
import kit.se.capstone2.file.application.FileAppService;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController implements FileDocsController {
	private final FileAppService fileAppService;

	@PostMapping("/images/profile")
	public CommonResponse<FileResponse> uploadFile(@RequestPart MultipartFile file){
		return CommonResponse.success(SuccessCode.CREATED, fileAppService.uploadProfile(file));
	}

	@DeleteMapping("/images/{id}")
	public CommonResponse<FileResponse> deleteImageFile(@PathVariable Long id){
		return CommonResponse.success(SuccessCode.DELETED, fileAppService.deleteFile(id));
	}


}
