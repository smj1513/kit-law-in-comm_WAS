package kit.se.capstone2.chat.domain.repository;

import kit.se.capstone2.chat.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
