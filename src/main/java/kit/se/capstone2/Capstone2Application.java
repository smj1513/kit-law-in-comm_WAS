package kit.se.capstone2;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class Capstone2Application {

	public static void main(String[] args) {
		SpringApplication.run(Capstone2Application.class, args);
	}

	//Timezone을 Asia/Seoul로 설정
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
