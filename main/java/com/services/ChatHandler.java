package com.services;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.entitys.Message;
import com.entitys.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHandler extends TextWebSocketHandler {
    private static final Logger logger = Logger.getLogger(ChatHandler.class.getName());

    @Autowired
    private UserLibraryServer userLibraryServer;

    @Autowired
    private MessageLibraryServer messageLibraryServer;

    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, String> userSessions = new ConcurrentHashMap<>(); // user -> sessionId, 没有使用static final但是依然只是一个，疑问？？
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userName = (String) session.getAttributes().get("user");
        System.out.println("username: " + userName);
        //username映射session的id，session的id映射session
        sessions.put(session.getId(), session);
        userSessions.put(userName, session.getId());

        System.out.println("usersessionsize: "+userSessions.size());

        // 发送历史消息给用户
        //通过chatwithController发送
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String[] payload = message.getPayload().split(":", 3);
        if (payload.length < 2) {
            // 消息格式不正确
            return;
        }

        //发过来的是用户主键，而不是用户名，需要的是用户名，所以通过用户主键寻找用户名
        String toUserId = payload[0];
        Integer touserid = Integer.parseInt(toUserId);
        User user = userLibraryServer.getUserById(touserid);
        if(user == null)
        {
            logger.warning("找不到用户id"+touserid);
            return;
        }
        String BookId = payload[1];
        Integer bookid = Integer.parseInt(BookId);
        
        String msg = payload[2];

        String toSessionId = userSessions.get(user.getUsername());
        if (toSessionId != null) {
            WebSocketSession toSession = sessions.get(toSessionId);
            if (toSession != null && toSession.isOpen()) {
                toSession.sendMessage(new TextMessage(bookid+":"+msg));
            }
        }

        // 保存消息到历史记录, 支持发向离线id
        messageLibraryServer.saveMessage(bookid, user.getUsername(), (String)session.getAttributes().get("user"), msg);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userName = (String) session.getAttributes().get("user");
        sessions.remove(session.getId());
        userSessions.remove(userName);
    }
}
