package org.example.quanlysu5.Config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.quanlysu5.Dto.Request.ThongBaoRequest;
import org.example.quanlysu5.Service.ThongBaoService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession>
            tokenSessions = new ConcurrentHashMap<>();
    private static final Map<String, List<WebSocketSession>>
            userSessions = new ConcurrentHashMap<>();
    private static final Map<String, List<WebSocketSession>>
            donViSessions = new ConcurrentHashMap<>();
    private static final Map<String, List<WebSocketSession>>
            vaiTroSessions = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ThongBaoService thongBaoService;



    @Override
    public void afterConnectionEstablished(
            WebSocketSession session
    ) {

        log.warn(
                "Client connected: {}",
                session.getId()
        );
    }
    @Override
    public void handleMessage(
            WebSocketSession session,
            WebSocketMessage<?> message
    ) throws Exception {

        log.warn("MESSAGE RECEIVED = {}", message.getPayload());

        super.handleMessage(session, message);
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {

        log.warn(
                "Received websocket message: {}",
                message.getPayload()
        );

        JsonNode json =
                mapper.readTree(
                        message.getPayload()
                );

        String type =
                json.path("type")
                        .asText();

        log.warn("TYPE = {}", type);

        if ("REGISTER".equals(type)) {
            registerSession(
                    json.path("token").asText(),
                    json.path("userId").asText(),
                    json.path("donViId").asText(),
                    json.path("role").asText(),
                    session
            );
        }
    }
    private void addUniqueSession(
            Map<String, List<WebSocketSession>> map,
            String key,
            WebSocketSession session
    ) {

        List<WebSocketSession> sessions =
                map.computeIfAbsent(
                        key,
                        k -> new CopyOnWriteArrayList<>()
                );

        if (!sessions.contains(session)) {
            sessions.add(session);
        }
    }
    private void removeSessionFromMap(
            Map<String, List<WebSocketSession>> map,
            WebSocketSession session
    ) {

        map.entrySet().removeIf(entry -> {

            entry.getValue().remove(session);

            return entry.getValue().isEmpty();
        });
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status
    ) {

        tokenSessions.values()
                .remove(session);

        removeSessionFromMap(userSessions, session);
        removeSessionFromMap(donViSessions, session);
        removeSessionFromMap(vaiTroSessions, session);

        log.warn(
                "Client disconnected: {}",
                session.getId()
        );
    }

    public void registerSession(
            String token,
            String userId,
            String donViId,
            String role,
            WebSocketSession session
    ) throws IOException {

        log.warn(
                "REGISTER => token={} user={} donvi={} role={}",
                token,
                userId,
                donViId,
                role
        );

        WebSocketSession oldSession =
                tokenSessions.put(token, session);
        List<WebSocketSession> oldSessions =
                userSessions.get(userId);

        if (oldSessions != null) {

            for (WebSocketSession old : oldSessions) {

                if (
                        old.isOpen()
                                && !old.getId().equals(session.getId())
                ) {

                    log.warn(
                            "FORCE_LOGOUT oldSession={} newSession={}",
                            old.getId(),
                            session.getId()
                    );

                    old.sendMessage(
                            new TextMessage(
                                    "{\"type\":\"FORCE_LOGOUT\"}"
                            )
                    );

                    old.close();
                }
            }

            oldSessions.removeIf(
                    s -> !s.isOpen()
            );
        }


        addUniqueSession(userSessions, userId, session);
        addUniqueSession(donViSessions, donViId, session);
        addUniqueSession(vaiTroSessions, role, session);



        long totalSessions =
                donViSessions.values()
                        .stream()
                        .mapToLong(List::size)
                        .sum();

        log.warn("Total sessions = {}", totalSessions);
        log.warn(
                "REGISTER SUCCESS user={} donvi={} role={}",
                userId,
                donViId,
                role
        );
    }

    public void sendToDonVi(
            String donViId,
            String message,
            ThongBaoRequest thongBaoRequest
    ) {

        List<WebSocketSession> sessions =
                donViSessions.get(donViId);

        if(
                sessions != null &&
                        !sessions.isEmpty()
        ){

            sessions.forEach(
                    s -> send(s, message)
            );

        }else{
            log.warn(thongBaoRequest.toString());

            thongBaoService
                    .createThongBaoDonVi(
                            thongBaoRequest
                    );
        }
    }
    public void sendToUser(String userId, String message,ThongBaoRequest thongBaoRequest) {
        List<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null&&!sessions.isEmpty()){

            sessions.forEach(
                    s -> send(s, message)
            );

        }else{
            log.warn("hhhhhh");
            log.warn(thongBaoRequest.toString());

            thongBaoService
                    .createThongBaoTaiKhoan(
                            thongBaoRequest
                    );
        }
    }

    public void sendToVaiTro(String role, String message) {
        List<WebSocketSession> sessions = vaiTroSessions.get(role);
        if (sessions == null) {
            return;
        }

        sessions.forEach(session -> send(session, message));
    }

    public void sendToToken(String token, String message) {
        WebSocketSession session = tokenSessions.get(token);
        if (session != null) {
            send(session, message);
        }
    }

    public void sendToAll(
            String message
    ) {

        tokenSessions.values()
                .forEach(
                        s -> send(s, message)
                );
    }

    private void send(
            WebSocketSession session,
            String message
    ) {

        try {

            if (session.isOpen()) {

                session.sendMessage(
                        new TextMessage(message)
                );
            }

        } catch (Exception e) {

            log.error(
                    "Send websocket error",
                    e
            );
        }
    }
    public boolean isDonViOnline(
            String donViId
    ){

        List<WebSocketSession> sessions =
                donViSessions.get(donViId);

        return sessions != null &&
                sessions.stream()
                        .anyMatch(
                                WebSocketSession::isOpen
                        );
    }
    public boolean isUserOnline(
            String idTaiKhoan
    ){
        List<WebSocketSession> sessions =
                userSessions.get(idTaiKhoan);

        return sessions != null
                && !sessions.isEmpty();
    }
}