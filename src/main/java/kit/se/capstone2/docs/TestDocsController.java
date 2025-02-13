package kit.se.capstone2.docs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDocsController {

	@GetMapping("/test")
	public Void testResponse(){
		return null;
	}
}
