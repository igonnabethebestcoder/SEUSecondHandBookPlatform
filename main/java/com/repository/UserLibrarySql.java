package com.repository;

import org.springframework.stereotype.Repository;

import com.entitys.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserLibrarySql extends JpaRepository<User, Integer>{

    
    Optional<User> findById(Integer id);

    //用户名必须唯一
    List<User> findByUsername(String username);


    @Modifying  
    @Transactional 
    @Query("DELETE FROM User u WHERE u.id = :userid")
    void deleteUserById(@Param("userid") Integer userid);
}

