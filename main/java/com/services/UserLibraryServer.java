package com.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.entitys.User;
import com.repository.UserLibrarySql;

/**
 * UserLibraryServer
 */
@Service
public class UserLibraryServer implements UserDetailsService {

    // private String accountID;
    // private int authority;//-1代表未登录，0普通用户，1管理员

    @Autowired  //自动注入
    private final UserLibrarySql userLibrarySql;  
    
    public UserLibraryServer(UserLibrarySql userLibrarySql) {  
        this.userLibrarySql = userLibrarySql;  
    }  
    
    public User getUserByUsername(String username)
    {
        List<User> userlist = userLibrarySql.findByUsername(username);
        if (userlist.size() == 1)
            return userlist.get(0);
        else
            return null;
    }

    public User getUserById(Integer id)
    {
        Optional<User> optionaluser = userLibrarySql.findById(id);
        User user = optionaluser.orElse(null);
        return user;
    }

    public void updateUserInfo(User user)
    {
        userLibrarySql.save(user);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入service,用户名为 : "  + username);
        if(username == null || username.isEmpty()){
            throw new UsernameNotFoundException("请输入用户名!");
        }
        List<SimpleGrantedAuthority>list = new ArrayList<>();
        User user = jdbcTemplate.queryForObject("select * from user where username = " + "\"" + username + "\"", new BeanPropertyRowMapper<>(User.class));
        list.add(new SimpleGrantedAuthority(user.getRole()));//用户权限
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }

    public User getCurrentUserBySeucurity()
    {
        //获取用户信息
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.getUserByUsername(userName);// 初始化为null  
        return user;
        //使用前需要判断user是否为null
    }

    public Integer getCurrentUseridBySeucurity()
    {
        if (this.getCurrentUserBySeucurity() != null)
            return this.getCurrentUserBySeucurity().getId();
        return 0;//不存在
    }

    //获取所有用户，用于在管理员页面进行展示
    public List<User> getAllUser() {
        List<User> userlist = userLibrarySql.findAll();
        return userlist;
    }

    //用户名查重
    public boolean checkUserDuplicated(String username) {
        List<User> user = userLibrarySql.findByUsername(username);
        if (user.size() != 0)
            return true;
        return false;
    }

    //添加用户
    public boolean addUser(String username)
    {
        boolean flag = this.checkUserDuplicated(username);
        if (flag)
            return false;
        User user = new User();
        user.setUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("123456"));//设置初始密码
        user.setRole("ROLE_user");
        user.setValid(true);
        userLibrarySql.save(user);
        flag = this.checkUserDuplicated(user.getUsername());
        if(flag)
            return true;
        return false;
    }

    //更新用户密码
    public boolean updateUserPassword(User user)
    {
        //如何检查用户密码是否更新成功？？
        userLibrarySql.save(user);
        return true;
    }

    //删除用户
    public boolean deleteUser(Integer userid)
    {
        userLibrarySql.deleteUserById(userid);
        Optional<User> optionaluser = userLibrarySql.findById(userid);
        User user = optionaluser.orElse(null);
        if (user == null)
            return true;
        return false;
    }

    //通过txt文件批量添加用户
    public List<String> addUsersByFile(String filePath) {
        List<String> unputList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String username;
            while ((username = br.readLine()) != null) 
            {
                // 处理读取到的每一行here
                if (!this.addUser(username))
                    unputList.add(username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return unputList;
    }
}

//
//User user = new User();
// user.setUsername(username);
// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
// user.setPassword(encoder.encode("123456"));//设置初始密码
// user.setRole("ROLE_user");
// user.setValid(true);