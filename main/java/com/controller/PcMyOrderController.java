package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entitys.Order;

import com.services.BookLibraryServer;
import com.services.OrderLibraryServer;
import com.services.UserLibraryServer;

@Controller
public class PcMyOrderController {
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private OrderLibraryServer orderLibraryServer;
    @Autowired
    private UserLibraryServer userLibraryServer;

    public PcMyOrderController(BookLibraryServer bookLibraryServer,OrderLibraryServer orderLibraryServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.orderLibraryServer = orderLibraryServer;
    }



    //-----------------------------------
    //购买记录
    //展示未确认的订单
    @RequestMapping("/user/unconfirmorder")
    public String jumpToPCunconfirmorderPage(HttpServletRequest request)
    {
        Integer userid = userLibraryServer.getCurrentUseridBySeucurity();
        if (userid.equals(0))
            return null;
        List<Order> orderlist = orderLibraryServer.getUnconfirmOrderByUserid(userid);
        request.setAttribute("orders", orderlist);
        return "user/PCunconfirmorderPage";
    }

    @RequestMapping("/user/unconfirmorder/load")
    @ResponseBody
    public List<Order> loadUnconfirmOrder()
    {
        Integer userid = userLibraryServer.getCurrentUseridBySeucurity();
        if (userid.equals(0))
            return null;
        List<Order> orderlist = orderLibraryServer.getUnconfirmOrderByUserid(userid);
        return orderlist;
    }

    
    //展示已确认的订单
    @RequestMapping("/user/hasconfirmorder")
    public String jumpToPChasconfirmorderPage(HttpServletRequest request)
    {
        Integer userid = userLibraryServer.getCurrentUseridBySeucurity();
        if (userid.equals(0))
            return null;
        List<Order> orderlist = orderLibraryServer.getHasConfirmOrderByUserid(userid);
        request.setAttribute("orders", orderlist);
        return "user/PChasconfirmorderPage";
    }

    @RequestMapping("/user/hasconfirmorder/load")
    @ResponseBody
    public List<Order> loadHasConfirmOrder()
    {
        Integer userid = userLibraryServer.getCurrentUseridBySeucurity();
        if (userid.equals(0))
            return null;
        List<Order> orderlist = orderLibraryServer.getHasConfirmOrderByUserid(userid);
        return orderlist;
    }
    

    //确认收货
    @RequestMapping("/user/unconfirmorder/confirming/{orderid}")
    @ResponseBody
    public boolean confirmingOrder(@PathVariable("orderid") Integer orderid)
    {
        Order order = orderLibraryServer.getOrderById(orderid);
        boolean flag = false;
        if (order != null)
            flag = orderLibraryServer.confirmOrder(order);
        return flag;
    }

    //申诉
    @PostMapping("/user/orderappeal/{orderid}")
    @ResponseBody
    public boolean appealOrder(@PathVariable("orderid") Integer orderid, @RequestBody String detail)
    {
        System.out.println("detail");
        Order order = orderLibraryServer.getOrderById(orderid);
        boolean flag = false;
        if (order != null)
            flag = orderLibraryServer.orderAppeal(order,detail);
        return flag;
    }

    @RequestMapping("/user/orderappeal/{orderid}/{detail}")
    @ResponseBody
    public boolean appealOrder2(@PathVariable("orderid") Integer orderid, @PathVariable("detail") String detail)
    {
        System.out.println("detail");
        Order order = orderLibraryServer.getOrderById(orderid);
        boolean flag = false;
        if (order != null)
            flag = orderLibraryServer.orderAppeal(order,detail);
        return flag;
    }

    //撤销申诉
    @RequestMapping("/user/revokeorderappeal/{orderid}")
    @ResponseBody
    public boolean revokeAppeal(@PathVariable("orderid") Integer orderid)
    {
        Order order = orderLibraryServer.getOrderById(orderid);
        boolean flag = false;
        if (order != null)
            flag = orderLibraryServer.revokeOrderAppeal(order);
        return flag;
    }

    @RequestMapping("/user/getappealoutcome/{orderid}")
    @ResponseBody
    public int getappealoutcome(@PathVariable("orderid") Integer orderid)
    {
        Order order = orderLibraryServer.getOrderById(orderid);
        if(order == null)
            return -2;
        return order.getAppealoutcome();
    }
}
