package kit.se.capstone2.chat.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.chat.domain.model.ChatMessage;
import kit.se.capstone2.chat.domain.model.ChatRoom;
import kit.se.capstone2.chat.domain.repository.ChatMessageRepository;
import kit.se.capstone2.chat.domain.repository.ChatRoomRepository;
import kit.se.capstone2.chat.domain.service.ChatMessageService;
import kit.se.capstone2.chat.interfaces.dto.request.ChatRequest;
import kit.se.capstone2.chat.interfaces.dto.response.ChatResponse;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatAppService {
	private final SecurityUtils securityUtils;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageService chatMessageService;
	private final ChatMessageRepository chatMessageRepository;
	private final AccountRepository accountRepository;

	public Slice<ChatResponse.ChatRoomRes> getChatRooms(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Account currentUserAccount = securityUtils.getCurrentUserAccount();
		BaseUser user = currentUserAccount.getUser();
		Slice<ChatRoom> chatRooms = chatRoomRepository.findByUserId(user.getId(), pageRequest);
		return chatRooms.map(chatRoom -> ChatResponse.ChatRoomRes.from(chatRoom, user));
	}

	public ChatResponse.ChatRoomRes createChatRoom(ChatRequest.CreateChatRoomReq request) {
		BaseUser currentUser = securityUtils.getCurrentUserAccount().getUser();
		Long otherPersonId = request.getOtherPersonId();
		Account account = accountRepository.findById(otherPersonId).orElseThrow(() -> new IllegalArgumentException("상대방이 존재하지 않습니다."));
		BaseUser otherUser = account.getUser();
		ChatRoom chatRoom = currentUser.createChat(otherUser);
		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		return ChatResponse.ChatRoomRes.from(savedChatRoom, currentUser);
	}

	public Slice<ChatResponse.ChatMessageRes> getChatMessages(Long chatRoomId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("sentAt")));
		BaseUser currentUser = securityUtils.getCurrentUserAccount().getUser(); //현재 사용자가 누구인지 확인

		Slice<ChatResponse.ChatMessageRes> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId, pageRequest)
				.map(chatMessage -> {
					chatMessage.readFrom(currentUser); //읽음 처리
					return ChatResponse.ChatMessageRes.builder()
							.messageId(chatMessage.getId())
							.senderId(chatMessage.getSender().getAccount().getUsername())
							.senderName(chatMessage.getSender().getName())
							.message(chatMessage.getMessage())
							.createdAt(chatMessage.getSentAt())
							.isRead(chatMessage.isRead())
							.build();
				});
		return chatMessages;
	}

	public ChatMessage saveMessage(Long chatRoomId, ChatRequest.ChatMessageReq request, Principal principal) {
		ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "채팅방이 존재하지 않습니다."));
		String name = principal.getName();
		Account account = accountRepository.findByUsername(name);
		BaseUser user = account.getUser();
		if(!chatRoom.isParticipant(user)){
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "채팅방에 참여하지 않은 사용자입니다.");
		}
		ChatMessage message = ChatMessage.builder().message(request.getContent())
				.sender(user)
				.build();
		chatRoom.addMessage(message);
		return chatMessageRepository.save(message);

	}
}
