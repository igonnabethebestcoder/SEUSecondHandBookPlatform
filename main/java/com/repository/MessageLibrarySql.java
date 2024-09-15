package com.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entitys.Message;


@Repository
public interface MessageLibrarySql extends JpaRepository<Message, Long>{

    @Query("SELECT m FROM Message m WHERE m.bookid = :bookid AND " +
    "((m.tousername = :toUserName AND m.fromusername = :fromUserName) OR " +
    "(m.tousername = :fromUserName AND m.fromusername = :toUserName))")
    List<Message> findMessageByAll(@Param("toUserName") String toUserName, 
                                @Param("fromUserName") String fromUserName, 
                                @Param("bookid") Integer bookid);
                                
}
    

