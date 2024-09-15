package com.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entitys.Book;
import com.entitys.Order;
import com.repository.OrderLibrarySql;

@Service
public class OrderLibraryServer {
    @Autowired
    private OrderLibrarySql orderLibrarySql;
    public OrderLibraryServer(OrderLibrarySql orderLibrarySql)
    {
        this.orderLibrarySql = orderLibrarySql;
    }

    public Order getOrderById(Integer orderid)
    {
        Optional<Order> optionalorder = orderLibrarySql.findById(orderid);
        Order order = optionalorder.orElse(null);
        return order;
    }

    //支付后生成订单
    public Order generateOrder(Integer userid, Book book)
    {
        Order order = new Order();
        order.setBuyerid(userid);
        order.setBookid(book.getId());
        order.setSellerid(book.getOwnerid());
        order.setHasappeal(0);
        order.setAppealoutcome(0);
        order.setBuyoutcome(0);
        LocalDateTime localDateTime = LocalDateTime.now();
        order.setHappenedtime(localDateTime);
        order.setHaspay(0);
        order.setPrice(book.getPrice());
        order.setBookname(book.getBookname());
        order.setImg(book.getImg());

        return order;
    }

    //保存订单
    public boolean saveOrder(Order order)
    {
        orderLibrarySql.save(order);
        return true;
    }


    //根据用户id查找该用户还未确认的订单
    public List<Order> getUnconfirmOrderByUserid(Integer userid)
    {
        List<Order> orderlist = orderLibrarySql.getUnconfirmOrderByUserid(userid);
        return orderlist;
    }

    //根据用户id查找该用户已经确认的订单
    public List<Order> getHasConfirmOrderByUserid(Integer userid)
    {
        List<Order> orderlist = orderLibrarySql.getHasConfirmOrderByUserid(userid);
        return orderlist;
    }

    //确认收货
    public boolean confirmOrder(Order order)
    {
        order.setBuyoutcome(1);
        orderLibrarySql.save(order);
        Order orderChecker = this.getOrderById(order.getId());
        if (orderChecker.getBuyoutcome() == 1)
            return true;
        return false;
    }

    //申诉
    public boolean orderAppeal(Order order, String detail)
    {
        order.setHasappeal(1);
        order.setDetail(detail);
        orderLibrarySql.save(order);
        Order orderChecker = this.getOrderById(order.getId());
        if (orderChecker.getHasappeal() == 1)
            return true;
        return false;
    }

    //撤销申诉
    public boolean revokeOrderAppeal(Order order)
    {
        order.setHasappeal(0);
        order.setDetail("无用户申诉");
        orderLibrarySql.save(order);
        Order orderChecker = this.getOrderById(order.getId());
        if (orderChecker.getHasappeal() == 0)
            return true;
        return false;
    }

    //获取没处理的申诉
    public List<Order> getNotHandleAppeal() {
        List<Order> orderlist = orderLibrarySql.getNotHandleOrder();
        return orderlist;
    }


    //获取已处理的申诉
    public List<Order> getHasHandleAppeal() {
        List<Order> orderlist = orderLibrarySql.getHasHandleOrder();
        return orderlist;
    }

    public Order getOrderByBookid(Integer bookid) {
        List<Order> orders = orderLibrarySql.findByBookid(bookid);
        Order order = orders.get(0);
        return order;
    }
}
