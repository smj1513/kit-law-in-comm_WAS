package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import kit.se.capstone2.chat.interfaces.dto.response.ChatResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "WebSocket", description = "WebSocket 엔드포인트 문서화")
@RestController
public class WebSocketDocumentationController {

    @Operation(
            summary = "웹 소켓 연결",
            description = "웹 소켓 연결 요청을 보냅니다. -> 채팅방 목록 진입 시점에 웹소켓 연결 필요"
    )
    @GetMapping("/ws-stomp")
    public void connection(){};

    @Operation(
        summary = "채팅 메시지 발행",
        description = "특정 채팅방에 메시지를 발행합니다."
    )
    @PostMapping("/pub/chat/{chatRoomId}")
    public ChatResponse.ChatMessageRes publishChatMessage(
        @Parameter(description = "채팅방 ID") @PathVariable Long chatRoomId,
        @Parameter(description = "채팅 메시지 요청") @RequestBody ChatRequest.ChatMessageReq request
    ) {
        return null;
    }

    @Operation(
        summary = "채팅방 메시지 구독",
        description = "특정 채팅방을 구독합니다."
    )
    @GetMapping("/sub/chat/{chatRoomId}")
    public ChatResponse.ChatMessageRes subscribeChatMessages(
        @Parameter(description = "채팅방 ID") @PathVariable Long chatRoomId
    ) {
        return null;
    }

//    @Operation(
//        summary = "메시지 읽음 상태 구독",
//        description = "특정 채팅방의 메시지 읽음 상태를 실시간으로 구독합니다."
//    )
//    @GetMapping("/sub/chat/{chatRoomId}/read")
//    public ChatResponse.ReadStatusRes subscribeReadStatus(
//        @Parameter(description = "채팅방 ID") @PathVariable Long chatRoomId
//    ) {
//        return null;
//    }

    @Operation(
        summary = "채팅방 목록 업데이트 구독",
        description = "사용자의 채팅방 목록 업데이트를 실시간으로 구독합니다."
    )
    @GetMapping("/sub/chatRooms/{userId}")
    public ChatResponse.ChatRoomUpdateRes subscribeChatRooms(
        @Parameter(description = "사용자 ID") @PathVariable Long userId
    ) {
        return null;
    }
} 