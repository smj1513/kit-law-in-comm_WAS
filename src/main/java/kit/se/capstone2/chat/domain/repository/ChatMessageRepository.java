package kit.se.capstone2.chat.domain.repository;

import kit.se.capstone2.chat.domain.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	@Query("select distinct cm from ChatMessage cm where cm.chatRoom.id = :chatRoomId")
	Slice<ChatMessage> findByChatRoomId(Long chatRoomId, Pageable pageable);

	@Query("SELECT cm FROM ChatMessage cm " +
			"WHERE cm.chatRoom.id = :chatRoomId " +
			"AND cm.isRead = false " +
			"AND cm.sender.id != :userId")
	List<ChatMessage> findUnreadMessages(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);
}
