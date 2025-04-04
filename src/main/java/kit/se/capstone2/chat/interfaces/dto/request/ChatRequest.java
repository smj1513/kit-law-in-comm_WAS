package kit.se.capstone2.chat.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatRequest {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ChatMessageReq{
		private String content;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CreateChatRoomReq {
		private Long otherPersonId;
	}
}
