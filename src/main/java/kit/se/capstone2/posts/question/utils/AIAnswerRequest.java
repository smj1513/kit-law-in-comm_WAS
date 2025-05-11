package kit.se.capstone2.posts.question.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIAnswerRequest {
	@JsonProperty(value = "question_id")
	private Long id;
	@JsonProperty(value = "title")
	private String title;
	@JsonProperty(value = "content")
	private String content;
}
