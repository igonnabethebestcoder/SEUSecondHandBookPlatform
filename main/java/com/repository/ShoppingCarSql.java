package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entitys.ShoppingCar;

@Repository
public interface ShoppingCarSql extends JpaRepository<ShoppingCar, Integer>{

    //查询

    //插入
    // 定义一个方法，通过用户ID查询购物车中的书籍ID列表  
    List<ShoppingCar> findByUserid(Integer userid);  


    //用于判断重复
    @Query("SELECT sc FROM ShoppingCar sc WHERE sc.userid = :userid AND sc.bookid = :bookid")  
    List<ShoppingCar> findShoppingCarByUseridAndBookid(@Param("userid") Integer userid, @Param("bookid") Integer bookid);


    @Modifying  
    @Transactional 
    @Query("DELETE FROM ShoppingCar sc WHERE sc.userid = :userid AND sc.bookid = :bookid")  
    void deleteByUseridAndBookid(@Param("userid") Integer userid, @Param("bookid") Integer bookid);
} 