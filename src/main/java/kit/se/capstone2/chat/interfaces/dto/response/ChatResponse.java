package kit.se.capstone2.chat.interfaces.dto.response;

import kit.se.capstone2.chat.domain.model.ChatMessage;
import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatResponse {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReadStatusRes{
		private Long readerId;
		@Builder.Default
		private List<Long> messageIds = new ArrayList<>();
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChatRoomRes {
		private Long chatRoomId;
		private String otherMemberName;
		private FileResponse otherMemberProfileImage;
		private String lastMessage;
		private LocalDateTime lastMessageAt;
		private int unreadMessageCount;

		//TODO: ChatRoom -> ChatRoomRes 변환
		public static ChatRoomRes from(ChatRoom chatRoom, BaseUser user) {
			BaseUser otherPerson = chatRoom.getOtherPerson(user);
			return ChatRoomRes.builder()
					.chatRoomId(chatRoom.getId())
					.otherMemberName(otherPerson.getNickname())
					.otherMemberProfileImage(otherPerson.getProfileImage() == null ? null : FileResponse.from(otherPerson.getProfileImage()))
					.lastMessage(chatRoom.getLastMessage() == null ? null : chatRoom.getLastMessage().getMessage())
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


	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ChatRoomUpdateRes{
		private Long chatRoomId;
		private Long otherPersonId;
		private FileResponse otherPersonProfileImage;
		private String lastMessage;
		private LocalDateTime lastMessageAt;
		private int unReadCount;

		public static ChatRoomUpdateRes from(ChatRoom chatRoom, BaseUser user){
			ChatMessage lastMessage1 = chatRoom.getLastMessage();
			BaseUser otherPerson = chatRoom.getOtherPerson(user);
			return ChatRoomUpdateRes.builder()
					.otherPersonId(otherPerson.getId())
					.otherPersonProfileImage(FileResponse.from(otherPerson.getProfileImage()))
					.chatRoomId(chatRoom.getId())
					.lastMessage(lastMessage1 == null ? null : lastMessage1.getMessage())
					.lastMessageAt(lastMessage1 == null ? null : lastMessage1.getSentAt())
					.unReadCount(chatRoom.getUnreadMessageCount())
					.build();
		}
	}
}
