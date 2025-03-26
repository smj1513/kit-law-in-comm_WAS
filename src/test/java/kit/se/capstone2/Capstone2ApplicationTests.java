package kit.se.capstone2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")  // 명시적 테스트 프로필 활성화
class Capstone2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
