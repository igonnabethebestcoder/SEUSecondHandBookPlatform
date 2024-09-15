package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entitys.ShoppingCar;
import com.repository.ShoppingCarSql;

@Service
public class ShoppingCarServer {
    @Autowired
    private ShoppingCarSql shoppingCarSql;
    public ShoppingCarServer(ShoppingCarSql shoppingCarSql)
    {
        this.shoppingCarSql = shoppingCarSql;
    }

    //添加到购物车
    public boolean add(ShoppingCar shoppingCar)
    {
        shoppingCarSql.save(shoppingCar);
        //需要有验证，后返回是否插入成功
        return true;
    }

    //获取购物车书籍id的list
    public List<Integer> getShoppingCarById(Integer id)
    {
        List<ShoppingCar> shoppingCars  = shoppingCarSql.findByUserid(id);
        List<Integer> bookidList = new ArrayList<>();
        for (int i = 0; i < shoppingCars.size();i++) {
            bookidList.add(shoppingCars.get(i).getBookid());
        }
        return bookidList;
    }

    public boolean findDuplicated(Integer userid, Integer bookid)
    {
        List<ShoppingCar> sc = shoppingCarSql.findShoppingCarByUseridAndBookid(userid, bookid);
        if(sc.size() >= 1)//存在
            return true;
        return false;
    }

    public boolean deleteShoppingCarByUseridAndBookid(Integer userid, Integer bookid)
    {
        shoppingCarSql.deleteByUseridAndBookid(userid, bookid);
        return !(this.findDuplicated(userid, bookid));
    }
}
