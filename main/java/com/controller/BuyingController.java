package com.controller;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entitys.Book;
import com.entitys.Order;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.OrderLibraryServer;
import com.services.UserLibraryServer;

@RestController
public class BuyingController {
    @Autowired
    private OrderLibraryServer orderLibraryServer;
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private UserLibraryServer userLibraryServer;
    public BuyingController(OrderLibraryServer orderLibraryServer,BookLibraryServer bookLibraryServer)
    {
        this.orderLibraryServer = orderLibraryServer;
        this.bookLibraryServer = bookLibraryServer;
    }


    //购买
    @RequestMapping("/user/buying/{bookid}/{ownerid}")
    public boolean buyBook(@PathVariable("bookid") Integer bookid, @PathVariable("ownerid") Integer ownerid,HttpServletRequest request)
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
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            // 在这里你可以选择记录日志、返回false或抛出异常等  
        }
        //Book book = bookLibraryServer.getBookById(bookid);
        if (ownerid.equals(user.getId()))
            return false;
        // //生成订单
        // Order order = orderLibraryServer.generateOrder(user.getId(), book);
        return true;
        // //使用request将order信息传到前端,存于一个session中
        // HttpSession session = request.getSession();  
        // session.setAttribute("order", order);

        //支付后退出，否则订单失效
    }

    @RequestMapping("/user/buying/{id}/paying")
    public boolean payBook(@PathVariable("id") Integer bookid, HttpServletRequest request)
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
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            // 在这里你可以选择记录日志、返回false或抛出异常等  
        }
        Book book = bookLibraryServer.getBookById(bookid);
        //生成订单
        Order order = orderLibraryServer.generateOrder(user.getId(), book);
        
        //考虑并发,isSoldOut相当于锁
        if(book.getIssoldout()== 0)
        {
            book.setIssoldout(1);
            bookLibraryServer.updateBookInfo(book);
        }
        else
            return false;
            

        //检查支付是否成功
        boolean flag = true;//checkpayment()

        if (flag)
        {
            //假设支付成功立马更新
            order.setHaspay(1);
            book.setBuyerid(order.getBuyerid());
            //book.setIssoldout(1);
            bookLibraryServer.updateBookInfo(book);//更新数据库

            //保存订单
            orderLibraryServer.saveOrder(order);
        }
        else
        {
            //支付失败
            book.setIssoldout(0);
            bookLibraryServer.updateBookInfo(book);
            return false;
        }
        return flag;
    }
}
