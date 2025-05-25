package kit.se.capstone2.chat.interfaces.controller;

import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.chat.application.ChatAppService;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import kit.se.capstone2.chat.interfaces.dto.response.ChatResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.ChatDocsController;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
public class ChatController implements ChatDocsController {

	//구현체로 SimpMessagingTemplate 사용
	private final SimpMessageSendingOperations messagingTemplate;
	private final SimpUserRegistry simpUserRegistry;
	private final ChatAppService chatService;
	private final SecurityUtils securityUtils;


	//특정 사용자의 채팅방 목록 조회
	@GetMapping("/chatRooms")
	public CommonResponse<Slice<ChatResponse.ChatRoomRes>> getChatRooms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return CommonResponse.success(SuccessCode.OK, chatService.getChatRooms(page, size));
	}

	//채팅방 생성
	@PostMapping("/chat/room")
	public CommonResponse<ChatResponse.ChatRoomRes> createChatRoom(@RequestBody ChatRequest.CreateChatRoomReq request) {
		return CommonResponse.success(SuccessCode.OK, chatService.createChatRoom(request));
	}

	//특정 채팅방의 메시지 목록 조회
	@GetMapping("/chat/room/{chatRoomId}")
	public CommonResponse<Slice<ChatResponse.ChatMessageRes>> getChatMessages(@PathVariable Long chatRoomId,
	                                                                          @RequestParam int page,
	                                                                          @RequestParam int size) {
		return CommonResponse.success(SuccessCode.OK, chatService.getChatMessages(chatRoomId, page, size));
	}

	//채팅 중 읽음 처리
	@MessageMapping("/chat/{chatRoomId}/read")
	public void markMessagesAsRead(@DestinationVariable Long chatRoomId,
	                               Principal principal) {
		chatService.readMessages(chatRoomId, principal);
	}


	//채팅 메시지 전송
	@MessageMapping("/chat/{chatRoomId}") // /pub/chat
	public void sendMessage(@RequestBody ChatRequest.ChatMessageReq request,
	                        @DestinationVariable Long chatRoomId,
	                        Principal principal // 추가
	) {
		chatService.sendMessage(request, chatRoomId, principal);
	}
}
