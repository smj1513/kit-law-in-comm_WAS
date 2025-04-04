package kit.se.capstone2.chat.interfaces.dto.response;

import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ChatResponse {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChatRoomRes {
		private Long chatRoomId;
		private String otherMemberName;
		private FileResponse otherMemberProfileImage;
		private LocalDateTime lastMessageAt;
		private int unreadMessageCount;

		//TODO: ChatRoom -> ChatRoomRes 변환
		public static ChatRoomRes from(ChatRoom chatRoom, BaseUser user) {
			BaseUser otherPerson = chatRoom.getOtherPerson(user);
			return ChatRoomRes.builder()
					.chatRoomId(chatRoom.getId())
					.otherMemberName(otherPerson.isLawyer() ? otherPerson.getName() : otherPerson.getNickname())
					.otherMemberProfileImage(otherPerson.getProfileImage() == null ? null : FileResponse.from(otherPerson.getProfileImage()))
					.lastMessageAt(chatRoom.getLastMessage() == null ? null : chatRoom.getLastMessage().getSentAt())
					.unreadMessageCount(chatRoom.getUnreadMessageCount())
					.build();
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ChatMessageRes {
		private Long messageId;
		private String senderId;
		private String senderName;
		private String message;
		private LocalDateTime createdAt;
		private boolean isRead;
	}

}
