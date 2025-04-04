package kit.se.capstone2.chat.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private BaseUser sender;

	@ManyToOne
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	private String message;

	@Column(name = "is_read")
	@Builder.Default
	private boolean isRead = false;

	@CreatedDate
	private LocalDateTime sentAt;

	public void readFrom(BaseUser reader) {
		//읽은 사람과 보낸 사람이 같을 때는 읽음 처리 안함
		//보낸 사람이 아닌 다른 사람 즉 상대방이 읽었을 때 읽음 처리
		if(!this.sender.getId().equals(reader.getId())) {
			this.isRead = true;
		}
	}
}
