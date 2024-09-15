package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entitys.Book;
import com.entitys.Lable;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.LableServer;
import com.services.UserLibraryServer;

/**
 * MainpageController
 */

@Controller//主页面的跳转操作
public class MainpageController {
    @Autowired
    private final BookLibraryServer bookLibraryServer;//需不需要final这是个问题
    @Autowired
    private UserLibraryServer userLibraryServer;

    @Autowired
    private final LableServer lableServer;
    public MainpageController(BookLibraryServer bookLibraryServer, LableServer lableServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.lableServer = lableServer;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request)
    {
        return this.uploadBooks(request);
    }

    //@RequestMapping(value = "/uploadBooks", method = RequestMethod.GET) // 如果需要特定的URL映射，可以添加这个注解  
    public String uploadBooks(HttpServletRequest request) { // 通常使用Model而不是HttpServletRequest来设置属性  
        User user = userLibraryServer.getCurrentUserBySeucurity();
        System.out.println(user.getRole());
        if (user.getRole().equals("ROLE_user"))
            request.setAttribute("role", 1);
        else
            request.setAttribute("role", 2);
        //展示还未售出的书籍
        List<Book> books = bookLibraryServer.getBooksNotSoldOut();  
        List<Lable> lables = lableServer.getAllLables();
        List<Lable> limitLables = lables.subList(0, Math.min(lables.size(), 4));
        request.setAttribute("Books", books); // 使用Model来添加属性到请求中  
        request.setAttribute("Lables", limitLables);
        return "common/main"; // 返回模板名称（如果common/main是相对于templates目录的路径）  
    }

    @GetMapping("/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, 
            @RequestParam(value = "count", defaultValue = "10") int count)
    {
        //分页获取书籍
        // List<Book> books = bookLibraryServer.getBooks(page);
        // request.setAttribute("books", books);
        return "/main";
    }
}