package kit.se.capstone2.user.domain.service;

import kit.se.capstone2.user.domain.model.BaseUser;

public interface UserService {
	void validateRemoveAuthority(BaseUser currentUser, BaseUser author);

	void validateModifyAuthority(BaseUser currentUser, BaseUser author);
}
