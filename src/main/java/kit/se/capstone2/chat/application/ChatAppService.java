package kit.se.capstone2.chat.application;

import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.chat.domain.repository.ChatRoomRepository;
import kit.se.capstone2.chat.domain.service.ChatMessageService;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import kit.se.capstone2.chat.interfaces.dto.response.ChatResponse;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatAppService {
	private final SecurityUtils securityUtils;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageService chatMessageService;

	public Slice<ChatResponse.ChatRoomRes> getChatRooms(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		BaseUser user = securityUtils.getCurrentUser().getUser();
		Slice<ChatRoom> chatRooms = chatRoomRepository.findByUserId(user.getId(), pageRequest);
		return chatRooms.map(chatRoom -> ChatResponse.ChatRoomRes.from(chatRoom, user));
	}

	public ChatResponse.ChatRoomRes createChatRoom(ChatRequest.CreateChatRoomReq request) {
		return null;
	}
}
