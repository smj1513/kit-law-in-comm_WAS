package kit.se.capstone2.chat.domain.repository;


import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.user.domain.model.BaseUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query("select distinct cr from ChatRoom cr where cr.creator.id = :userId or cr.participant.id = :userId order by cr.lastMessageAt desc")
	List<ChatRoom> findByUserId(Long userId);


	@Query("select distinct cr from ChatRoom cr where (cr.creator = :currentUser and cr.participant = :otherUser) or (cr.creator = :otherUser and cr.participant = :currentUser)")
	Optional<ChatRoom> findByParticipants(BaseUser currentUser, BaseUser otherUser);

	@Query("select count(cr) >= :limit from ChatRoom cr where cr.creator = :user or cr.participant = :user")
	boolean hasExceededCreationLimit(BaseUser user, int limit);
}
