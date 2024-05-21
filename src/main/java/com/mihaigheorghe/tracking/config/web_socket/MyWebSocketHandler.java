package com.mihaigheorghe.tracking.config.web_socket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages here
        String payload = message.getPayload();
        System.out.println("payload: " + payload);
        session.sendMessage(new TextMessage("Received: " + payload));
    }
}