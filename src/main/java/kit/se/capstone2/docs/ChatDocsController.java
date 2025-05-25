package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import kit.se.capstone2.chat.interfaces.dto.response.ChatResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import org.springframework.data.domain.Slice;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Chat", description = "채팅 API")
public interface ChatDocsController {

	//특정 사용자의 채팅방 목록 조회
	@Operation(summary = "채팅방 목록 조회", description = "특정 사용자의 채팅방 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공")
	public CommonResponse<List<ChatResponse.ChatRoomRes>> getChatRooms(int page, int size);

	//채팅방 생성
	@Operation(summary = "채팅방 생성", description = "채팅방을 생성합니다.")
	@ApiResponse(responseCode = "201", description = "채팅방 생성 성공")
	public CommonResponse<ChatResponse.ChatRoomRes> createChatRoom(ChatRequest.CreateChatRoomReq request);

	//특정 채팅방의 메시지 목록 조회

	@Operation(summary = "채팅방의 메시지 기록 조회", description = "특정 채팅방의 메시지 기록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "메시지 기록 조회 성공")
	public CommonResponse<Slice<ChatResponse.ChatMessageRes>> getChatMessages(@PathVariable Long chatRoomId, @RequestParam int page, @RequestParam int size);

	@Operation(summary = "메시지 전송", description = "특정 채팅방에 메시지를 전송합니다.")
	@ApiResponse(responseCode = "200", description = "메시지 전송 성공")
	public void sendMessage(ChatRequest.ChatMessageReq request, Long chatRoomId, Principal principal);

	public void markMessagesAsRead(@DestinationVariable Long chatRoomId, Principal principal);
}
