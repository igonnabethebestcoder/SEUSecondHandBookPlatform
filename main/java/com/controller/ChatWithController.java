package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entitys.Message;
import com.entitys.User;
import com.services.MessageLibraryServer;
import com.services.UserLibraryServer;

@Controller
public class ChatWithController {
    //通过mapping将历史消息加载
    @Autowired
    private UserLibraryServer userLibraryServer;
    
    @Autowired
    private MessageLibraryServer messageLibraryServer;

    @RequestMapping("/chat/{bookid}/{touserid}")
    @ResponseBody
    public List<Message> loadHistory(@PathVariable("bookid") Integer bookid, @PathVariable("touserid") Integer touserid)
    {
        System.out.println("bookid:" +bookid);
        System.out.println("buyerid:" +touserid);
        User touser = userLibraryServer.getUserById(touserid);
        if(touser == null)
            return null;
        String toUsername = touser.getUsername();
        
        
        User me = userLibraryServer.getCurrentUserBySeucurity();
        List<Message> messages = messageLibraryServer.getHistoryMessageByBookFromAndTo(bookid, toUsername, me.getUsername());
        Message sysmsg = new Message();
        sysmsg.setFromusername(me.getUsername());
        sysmsg.setTousername(toUsername);
        sysmsg.setBookid(0);
        messages.add(0, sysmsg);
        return messages;
    }
}
