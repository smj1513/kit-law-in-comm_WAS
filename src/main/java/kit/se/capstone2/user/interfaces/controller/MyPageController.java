package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/my-page")
public class MyPageController {

	@GetMapping
	public CommonResponse<MyPageResponse.GeneralInfo> getGeneralInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/lawyer")
	public CommonResponse<MyPageResponse.LawyerInfo> getLawyerInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

}
