package kit.se.capstone2.chat.domain.model;

import jakarta.persistence.*;
import kit.se.capstone2.common.entity.BaseTime;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private BaseUser creator;

	@ManyToOne
	@JoinColumn(name = "participant_id")
	private BaseUser participant;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "last_message_id")
	private ChatMessage lastMessage;

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Builder.Default
	private List<ChatMessage> chatMessages = new ArrayList<>();

	//TODO: 상대방이 보낸 메시지 중에서 읽지 않은 메시지의 개수만 나와야하는데,,, 이대로면 내가 보낸 메시지도 포함되어서 나옴
	@Formula("(select count(*) from chat_message cm where cm.chat_room_id = id and cm.is_read = false)")
	private int unreadMessageCount;

	public BaseUser getOtherPerson(BaseUser pivot) {
		if (creator.getId().equals(pivot.getId())) {
			return participant;
		} else {
			return creator;
		}
	}

}
