package kit.se.capstone2.chat.domain.service;

import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;

public interface ChatMessageService {
	void sendMessage(Long chatRoomId, ChatRequest.ChatMessageReq chatMessageReq);
}
