package com.chuuzr.chuuzrbackend.security;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.chuuzr.chuuzrbackend.dto.auth.UserInternalDTO;
import com.chuuzr.chuuzrbackend.error.ErrorCode;
import com.chuuzr.chuuzrbackend.exception.AuthorizationException;
import com.chuuzr.chuuzrbackend.repository.RoomUserRepository;
import com.chuuzr.chuuzrbackend.service.auth.AuthService;

@Component
public class StompAuthChannelInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(StompAuthChannelInterceptor.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROOM_TOPIC_PREFIX = "/topic/rooms/";

    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final RoomUserRepository roomUserRepository;

    @Autowired
    public StompAuthChannelInterceptor(JwtUtil jwtUtil, AuthService authService,
            RoomUserRepository roomUserRepository) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.roomUserRepository = roomUserRepository;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();
        logger.debug("STOMP {} frame received", command);

        if (StompCommand.CONNECT.equals(command)) {
            accessor.setUser(authenticate(accessor));
        }

        if (StompCommand.SUBSCRIBE.equals(command)) {
            authorizeSubscription(accessor);
        }

        return message;
    }

    private UsernamePasswordAuthenticationToken authenticate(StompHeaderAccessor accessor) {
        String authHeader = accessor.getFirstNativeHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            throw new AuthorizationException(ErrorCode.JWT_INVALID,
                    "Missing or malformed Authorization header in STOMP CONNECT");
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        if (!jwtUtil.validateAccessToken(token)) {
            throw new AuthorizationException(ErrorCode.JWT_INVALID,
                    "Invalid or expired JWT in STOMP CONNECT");
        }

        String role = jwtUtil.extractRole(token);
        if (!"ROLE_USER".equals(role)) {
            throw new AuthorizationException(ErrorCode.AUTHORIZATION_FAILED,
                    "WebSocket connections require ROLE_USER");
        }

        UUID userUuid = UUID.fromString(jwtUtil.extractSubject(token));
        UserInternalDTO userContext = authService.getInternalUserContext(userUuid);

        Set<SimpleGrantedAuthority> authorities = userContext.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        logger.info("STOMP CONNECT authenticated for user={}", userContext.getUuid());
        return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
    }

    private void authorizeSubscription(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();

        if (destination == null || !destination.startsWith(ROOM_TOPIC_PREFIX)) {
            return;
        }

        String roomUuidStr = destination.substring(ROOM_TOPIC_PREFIX.length());
        UUID roomUuid;
        try {
            roomUuid = UUID.fromString(roomUuidStr);
        } catch (IllegalArgumentException e) {
            throw new AuthorizationException(ErrorCode.AUTHORIZATION_FAILED,
                    "Invalid room UUID in subscription destination: " + destination);
        }

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) accessor.getUser();

        if (auth == null || !(auth.getPrincipal() instanceof UserInternalDTO)) {
            throw new AuthorizationException(ErrorCode.AUTHORIZATION_FAILED,
                    "Unauthenticated SUBSCRIBE frame");
        }

        UserInternalDTO userContext = (UserInternalDTO) auth.getPrincipal();
        boolean isMember = roomUserRepository.findByUuids(roomUuid, userContext.getUuid()).isPresent();

        if (!isMember) {
            logger.warn("SUBSCRIBE denied: user={} is not a member of room={}",
                    userContext.getUuid(), roomUuid);
            throw new AuthorizationException(ErrorCode.AUTHORIZATION_FAILED,
                    "User is not a member of room " + roomUuid);
        }

        logger.info("SUBSCRIBE authorized: user={} on room={}", userContext.getUuid(), roomUuid);
    }
}
