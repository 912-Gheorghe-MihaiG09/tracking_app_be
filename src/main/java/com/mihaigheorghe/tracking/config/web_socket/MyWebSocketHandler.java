package com.mihaigheorghe.tracking.config.web_socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler extends TextWebSocketHandler {

    // Thread-safe set to keep track of all connected sessions
    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the new session to the set
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages here
        String payload = message.getPayload();
        System.out.println("payload: " + payload);


        // Broadcast the message to all connected clients
        broadcastMessage(payload);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the set
        sessions.remove(session);
    }

    private void broadcastMessage(String message) {
        // Send the message to all connected sessions
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (Exception e) {
                // Handle the exception (log it, notify someone, etc.)
                e.printStackTrace();
            }
        }
    }
}
