package com.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.services.UserLibraryServer;

@EnableWebSecurity//开启mvc security安全支持
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //使用JDBC进行身份验证
    @Autowired
    private UserLibraryServer userLibraryServer;
    
    @Override//重写AuthenticationManagerBuilder auth
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //密码需要设置编码器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//必须要使用encoder，不然报错
        auth.userDetailsService(userLibraryServer)
            .passwordEncoder(encoder);
    }

    @Value("${COOKIE.VALIDITY}")
    private Integer COOKIE_VALIDITY;
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/css/**", "/js/**").permitAll()//当启用security后，
            //Spring Security默认会保护所有的URL路径，包括那些指向静态资源的路径（如CSS、JavaScript和图片文件）
            //.antMatchers("/","/user/**", "/manager/**","/common/**").permitAll()
            //--------------
            .antMatchers("/common/**","/","/chat/**").authenticated()//登录后才能访问
            .antMatchers("/user/**","/user/orderappeal/**").hasRole("user")//上传书籍需要有user角色
            .antMatchers("/manager/**").hasRole("manager")//尚未实现
            //--------------
            .anyRequest().authenticated()//对于除了上面指定的路径之外的所有其他请求，都需要用户进行认证。
            .and()//这个方法表示结束当前的配置链，并开始一个新的配置链。

            .formLogin()
            .usernameParameter("username").passwordParameter("password")
            .defaultSuccessUrl("/")
            ;
    }
}
