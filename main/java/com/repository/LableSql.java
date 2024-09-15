package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entitys.Lable;

@Repository
public interface LableSql extends JpaRepository<Lable, Integer>{
    List<Lable> findAll();

    Optional<Lable> findById(Integer id);

    //根据id修改标签

    //查重
    List<Lable> findByLable(String lable);
} 
