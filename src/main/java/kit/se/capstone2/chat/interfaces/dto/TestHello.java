package kit.se.capstone2.chat.interfaces.dto;

import kit.se.capstone2.chat.domain.model.ChatRoom;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestHello {
	private String roomId;
	private String name;

	public static TestHello create(String name) {
		return TestHello.builder()
				.roomId(UUID.randomUUID().toString())
				.name(name)
				.build();
	}
}
