package com.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entitys.Message;
import com.repository.MessageLibrarySql;

@Service
public class MessageLibraryServer {
    @Autowired
    private MessageLibrarySql messageLibrarySql;

    public List<Message> getHistoryMessageByBookFromAndTo(Integer bookid, String toUser, String fromUser)
    {
        List<Message> messages = messageLibrarySql.findMessageByAll(toUser, fromUser, bookid);
        return messages;
    }

    public void saveMessage(Integer bookid, String touser, String fromuser, String msg)
    {
        Message message = new Message();
        message.setBookid(bookid);
        message.setFromusername(fromuser);
        message.setTousername(touser);
        message.setMsg(msg);
        LocalDateTime localDateTime = LocalDateTime.now();
        message.setTime(localDateTime);
        messageLibrarySql.save(message);
    } 
}
