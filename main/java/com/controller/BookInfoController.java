package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entitys.Book;
import com.entitys.Order;
import com.entitys.ShoppingCar;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.OrderLibraryServer;
import com.services.ShoppingCarServer;
import com.services.UserLibraryServer;

@Controller
public class BookInfoController {

    @Autowired
    private BookLibraryServer bookLibraryServer;

    @Autowired
    private ShoppingCarServer shoppingCarServer;

    @Autowired
    private OrderLibraryServer orderLibraryServer;

    @Autowired
    private UserLibraryServer userLibraryServer;
    public BookInfoController(BookLibraryServer bookLibraryServer,ShoppingCarServer shoppingCarServer,OrderLibraryServer orderLibraryServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.shoppingCarServer = shoppingCarServer;
        this.orderLibraryServer = orderLibraryServer;
    }

    //-------------------------
    //查看id号书籍的详情
    @RequestMapping("/common/bookinfo/{id}")
    public String showBookInfo(@PathVariable("id") Integer id, HttpServletRequest request)
    {
        Book book = bookLibraryServer.getBookById(id);
        request.setAttribute("book", book);

        //默认生成订单
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null; // 初始化为null  
        try {  
            user = userLibraryServer.getUserByUsername(userName);  
            System.out.println(user);
            if (user == null) {  
                // 处理找不到用户的情况,返回登录界面
                // 这里可以返回false或抛出异常等  
                return "login";
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            // 在这里你可以选择记录日志、返回false或抛出异常等  
            return "login";
        }

        //在点击书记页面的时候就已经默认生成订单
        Order order = orderLibraryServer.generateOrder(user.getId(), book);
        // HttpSession session = request.getSession();  
        // session.setAttribute("order", order);
        request.setAttribute("order", order);
        return "common/bookinfo";
    }
    //--------------------------


    //添加到购物车
    @RequestMapping("/user/bookinfo/{id}/addToShoppingCar")
    @ResponseBody
    public boolean addToShoppingCar(@PathVariable("id") Integer bookid, HttpServletRequest request)
    {
        //获取用户信息
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null; // 初始化为null  
        try {  
            user = userLibraryServer.getUserByUsername(userName);  
            System.out.println(user);
            if (user == null) {  
                // 处理找不到用户的情况  
                // 这里可以返回false或抛出异常等  
                return false;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }

        ShoppingCar shoppingCar = new ShoppingCar();
        shoppingCar.setUserid(user.getId());
        shoppingCar.setBookid(bookid);

        //避免重复加入购物车
        boolean flag =true;
        if (!shoppingCarServer.findDuplicated(user.getId(), bookid))
            flag = shoppingCarServer.add(shoppingCar);
        //判断flag，看是否加入购物车成功
        return flag;
    }

}
