package kit.se.capstone2.chat.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ChatDto {


	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Message<T>{
		private MessageType type;
		private String sender;
		private Long ChatRoomId;
		private T data;

	}
}
