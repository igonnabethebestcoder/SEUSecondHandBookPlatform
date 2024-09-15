package com.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entitys.Order;

@Repository
public interface OrderLibrarySql extends JpaRepository<Order, Integer>{

    //jpa自带save函数，保存或是更新数据库
    Optional<Order> findById(Integer id);

    @Query("SELECT o FROM Order o WHERE o.buyerid = :buyerid AND o.buyoutcome = 0")
    List<Order> getUnconfirmOrderByUserid(@Param("buyerid") Integer buyerid);

    @Query("SELECT o FROM Order o WHERE o.buyerid = :buyerid AND o.buyoutcome = 1")
    List<Order> getHasConfirmOrderByUserid(@Param("buyerid") Integer buyerid);


    @Query("SELECT o FROM Order o WHERE o.hasappeal = 1 AND o.appealoutcome = 0")
    List<Order> getNotHandleOrder();

    @Query("SELECT o FROM Order o WHERE o.hasappeal = 1 AND o.appealoutcome != 0")
    List<Order> getHasHandleOrder();

    List<Order> findByBookid(Integer bookid);
    
}
