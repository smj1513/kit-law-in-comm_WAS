package kit.se.capstone2.posts.question.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AsyncRequestUtils {

	private final RestTemplate restTemplate;
	@Value("${ai-server.url}")
	private String url;

	@Async
	public void sendAsyncRequest(AIAnswerRequest aiAnswerRequest){
		restTemplate.postForEntity(url, aiAnswerRequest, Void.class);
	}
}
