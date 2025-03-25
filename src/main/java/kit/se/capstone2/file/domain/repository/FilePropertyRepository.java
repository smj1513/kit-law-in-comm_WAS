package kit.se.capstone2.file.domain.repository;

import kit.se.capstone2.file.domain.model.FileProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePropertyRepository extends JpaRepository<FileProperty,Long> {
}
