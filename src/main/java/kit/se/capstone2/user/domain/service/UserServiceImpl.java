package kit.se.capstone2.user.domain.service;

import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

	@Override
	public void validateRemoveAuthority(BaseUser currentUser, BaseUser author) {
		if (!(currentUser.getId().equals(author.getId()) || currentUser.isAdmin())) {
			log.info("권한 없음 -----");
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "삭제 권한이 없습니다.");
		}
	}

	@Override
	public void validateModifyAuthority(BaseUser currentUser, BaseUser author) {
		if (!currentUser.getId().equals(author.getId())) {
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "수정 권한이 없습니다.");
		}
	}


}
