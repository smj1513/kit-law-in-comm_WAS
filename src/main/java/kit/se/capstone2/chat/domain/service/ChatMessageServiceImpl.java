package kit.se.capstone2.chat.domain.service;

import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.chat.domain.repository.ChatRoomRepository;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService{
	private final ChatRoomRepository chatRoomRepository;

	@Override
	public void sendMessage(Long chatRoomId, ChatRequest.ChatMessageReq chatMessageReq) {
		ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

	}
}
