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
import kit.se.capstone2.common.exception.ChatRoomAlreadyExistsException;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatAppService {
	private final SecurityUtils securityUtils;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageService chatMessageService;
	private final ChatMessageRepository chatMessageRepository;
	private final AccountRepository accountRepository;
	private final SimpMessageSendingOperations messagingTemplate;

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
		Account account = accountRepository.findById(otherPersonId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "상대방이 존재하지 않습니다."));
		BaseUser otherUser = account.getUser();
		Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByParticipants(currentUser, otherUser);
		chatRoomOptional.ifPresent(cr -> {
			Long id = cr.getId();
			throw new ChatRoomAlreadyExistsException(ErrorCode.ALREADY_CHATROOM_CREATED, "채팅방이 이미 존재합니다.", Map.of("chatRoomId", id));
		});
		ChatRoom chatRoom = currentUser.createChat(otherUser);
		ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
		return ChatResponse.ChatRoomRes.from(savedChatRoom, currentUser);
	}

	public Slice<ChatResponse.ChatMessageRes> getChatMessages(Long chatRoomId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("sentAt")));
		BaseUser currentUser = securityUtils.getCurrentUserAccount().getUser(); //현재 사용자가 누구인지 확인

		//읽음 처리
		return chatMessageRepository.findByChatRoomId(chatRoomId, pageRequest)
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
	}

	public ChatMessage saveMessage(Long chatRoomId, ChatRequest.ChatMessageReq request, BaseUser user) {
		ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "채팅방이 존재하지 않습니다."));
		if (!chatRoom.isParticipant(user)) {
			throw new BusinessLogicException(ErrorCode.NO_PERMISSION, "채팅방에 참여하지 않은 사용자입니다.");
		}
		ChatMessage message = ChatMessage.builder().message(request.getContent())
				.sender(user)
				.build();
		chatRoom.addMessage(message);
		ChatMessage savedChatMessage = chatMessageRepository.save(message);

		ChatRoom chatRoom1 = savedChatMessage.getChatRoom();
		chatRoom1.setLastMessage(savedChatMessage);
		return savedChatMessage;
	}

	public void readMessages(Long chatRoomId, Principal principal) {
		String name = principal.getName();
		BaseUser currentUser = accountRepository.findByUsername(name).getUser();
		List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(chatRoomId, currentUser.getId());

		for (ChatMessage message : unreadMessages) {
			message.readFrom(currentUser);
		}
		// 읽음 처리된 메시지 목록을 발신자에게 알림
		messagingTemplate.convertAndSend(
				"/sub/chat/" + chatRoomId + "/read",
				ChatResponse.ReadStatusRes.builder()
						.readerId(currentUser.getId())
						.messageIds(unreadMessages.stream().map(ChatMessage::getId).collect(Collectors.toList()))
						.build()
		);
	}


	public void sendUpdatedChatRoomInfo(BaseUser user) {
		PageRequest pageRequest = PageRequest.of(0, 30, Sort.by(Sort.Order.desc("last_message_at")));

		Slice<ChatRoom> chatRooms = chatRoomRepository.findByUserId(user.getId(), pageRequest);
		messagingTemplate.convertAndSend("/sub/chatRoomList/" + user.getAccount().getUsername(),
				chatRooms.map(chatRoom -> ChatResponse.ChatRoomUpdateRes.from(chatRoom, user))
		);
	}

	/**
	 * 메시지 보내는 시점에 해야되는거...
	 * 1. 메시지 저장
	 * 2. 현재 채팅방 목록을 보고 있는 사람에게 채팅방의 약식 정보를 보내줘야함.(구독 채널 하나 별도로 만들어서..)
	 * 3. 메시지 전송
	 */
	public void sendMessage(ChatRequest.ChatMessageReq request, Long chatRoomId, Principal principal) {
		String name = principal.getName();
		Account account = accountRepository.findByUsername(name);
		BaseUser user = account.getUser();
		ChatMessage chatMessage = saveMessage(chatRoomId, request, user);
		messagingTemplate.convertAndSend("/sub/chat/" + chatRoomId,
				ChatResponse.ChatMessageRes.builder()
						.senderName(chatMessage.getSender()
								.getResponseName())
						.message(chatMessage.getMessage())
						.senderId(chatMessage.getSender().getAccount().getUsername())
						.messageId(chatMessage.getId())
						.createdAt(chatMessage.getSentAt())
						.isRead(chatMessage.isRead()) // 이 부분은 생각좀 해야할듯..
						.build()
		);
		sendUpdatedChatRoomInfo(user);
	}
}
