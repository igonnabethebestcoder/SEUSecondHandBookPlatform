package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.entitys.Book;
import com.entitys.Order;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.OrderLibraryServer;
import com.services.UserLibraryServer;

@Controller
public class PcMyBookController {
    @Autowired
    private BookLibraryServer bookLibraryServer;

    @Autowired
    private UserLibraryServer userLibraryServer;

    @Autowired
    private OrderLibraryServer orderLibraryServer;

    public PcMyBookController(BookLibraryServer bookLibraryServer)
    {
        this.bookLibraryServer = bookLibraryServer;
    }

    //-----------------------------------
    //我的上传
    @RequestMapping("/user/mybook")
    public String jumpToPCmyBook(HttpServletRequest request)
    {
        //获取用户信息
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null; // 初始化为null  
        try {  
            user = userLibraryServer.getUserByUsername(userName);  
            System.out.println(user);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        List<Book> bookList = bookLibraryServer.getNotSoldBookListByOwnerid(user.getId());
        request.setAttribute("Books", bookList);
        return "user/PCmyBook";
    }

    @RequestMapping("/user/mybook/Load")
    @ResponseBody
    public List<Book> loadMyBooks()
    {
        //获取用户信息
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null; // 初始化为null  
        try {  
            user = userLibraryServer.getUserByUsername(userName);  
            System.out.println(user);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        List<Book> bookList = bookLibraryServer.getNotSoldBookListByOwnerid(user.getId());
        System.out.println(user.getId());
        System.out.println(bookList.size());
        return bookList;
    }
    //-----------------------------------end


    //-----------------------------------
    //售出记录
    @RequestMapping("/user/soldRecords")
    public String jumpToPCsoldRecords(HttpServletRequest request)
    {
        User user = userLibraryServer.getCurrentUserBySeucurity();
        List<Book> booklist = null;
        if(user != null)
            booklist = bookLibraryServer.getHasSoldBookListByOwnerid(user.getId());
        request.setAttribute("Books", booklist);
        return "user/PCsoldRecords";
    }

    @RequestMapping("/user/soldRecords/load")
    @ResponseBody
    public List<Book> loadSoldRecords()
    {
        User user = userLibraryServer.getCurrentUserBySeucurity();
        List<Book> booklist = null;
        if(user != null)
            booklist = bookLibraryServer.getHasSoldBookListByOwnerid(user.getId());
        return booklist;
    }

    //售出记录页面展示申诉详情
    @RequestMapping("/user/appealDetail/{ownerid}/{buyerid}/{bookid}")
    @ResponseBody
    public Order loadAppealDetail(@PathVariable("ownerid") Integer ownerid, 
                                        @PathVariable("buyerid") Integer buyerid,
                                        @PathVariable("bookid") Integer bookid)
    {
        Order order = orderLibraryServer.getOrderByBookid(bookid);
        return order;
    }

    //售出记录页面展示订单详情
    @RequestMapping("/user/orderDetail/{ownerid}/{buyerid}/{bookid}")
    @ResponseBody
    public Order loadOrderDetail(@PathVariable("ownerid") Integer ownerid, 
                                        @PathVariable("buyerid") Integer buyerid,
                                        @PathVariable("bookid") Integer bookid)
    {
        Order order = orderLibraryServer.getOrderByBookid(bookid);
        return order;
    }
    //-----------------------------------end

    
}
