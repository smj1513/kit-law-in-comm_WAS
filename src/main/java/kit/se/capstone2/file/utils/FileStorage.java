package kit.se.capstone2.file.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileStorage {
	void store(MultipartFile file, String path);
	void delete(String path);
}
