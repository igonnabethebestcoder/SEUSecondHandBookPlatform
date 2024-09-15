package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entitys.Book;

import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.ShoppingCarServer;
import com.services.UserLibraryServer;

@Controller
public class PcShoppingCarController {
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private ShoppingCarServer shoppingCarServer;
    @Autowired
    private UserLibraryServer userLibraryServer;

    public PcShoppingCarController(BookLibraryServer bookLibraryServer,ShoppingCarServer shoppingCarServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.shoppingCarServer = shoppingCarServer;
    }

    //-----------------------------------
    //购物车
    @RequestMapping("/user/shoppinglist")
    public String jumpToPCshoppinglist(HttpServletRequest request)
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

        //需要通过shoppingCarServer获取收藏的书籍的id
        List<Integer> bookList = shoppingCarServer.getShoppingCarById(user.getId());
        //再通过BookLibraryServer获取id的详情信息
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < bookList.size();i++)
        {
            Book book = bookLibraryServer.getBookById(bookList.get(i));
            if (book.getId() != -1)
                books.add(book);
        }
        request.setAttribute("books", books);
        return "user/PCshoppingList";
    }

    @RequestMapping("/user/shoppinglist/Load")
    @ResponseBody
    public List<Book> loadShoppingList()
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

        //需要通过shoppingCarServer获取收藏的书籍的id
        List<Integer> bookList = shoppingCarServer.getShoppingCarById(user.getId());
        //再通过BookLibraryServer获取id的详情信息
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < bookList.size();i++)
        {
            Book book = bookLibraryServer.getBookById(bookList.get(i));
            if (book.getId() != -1)
                books.add(book);
        }
        //最后返回json文件格式
        return books;
    }


    @RequestMapping("/user/shoppinglist/delete/{bookid}")
    @ResponseBody
    public boolean deleteShoppingCarByUseridAndBookid(@PathVariable("bookid") Integer bookid)
    {
        Integer userid = userLibraryServer.getCurrentUseridBySeucurity();
        if (userid == 0)
            return false;

        return shoppingCarServer.deleteShoppingCarByUseridAndBookid(userid, bookid);
    }
    //-----------------------------------end
}
