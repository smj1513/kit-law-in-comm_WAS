package kit.se.capstone2.user.domain.service;

import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
	@Override
	public void validateRemoveAuthority(BaseUser currentUser, BaseUser author) {
		if (!currentUser.equals(author) || !currentUser.isAdmin()) {
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "삭제 권한이 없습니다.");
		}
	}

	@Override
	public void validateModifyAuthority(BaseUser currentUser, BaseUser author) {
		if (!currentUser.equals(author)) {
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "수정 권한이 없습니다.");
		}
	}


}
