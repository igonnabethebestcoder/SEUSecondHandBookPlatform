package com.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override  
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**")  
                //.allowedOriginPatterns("*") // 注意：这里使用allowedOriginPatterns而不是allowedOrigins  
                .allowedMethods("GET", "POST", "PUT", "DELETE")  
                .allowedHeaders("*");
                // 注意：当你使用 "*" 作为允许的源时，不应该设置 allowCredentials(true)  
                // .allowCredentials(true); // 这行应该被注释掉或删除   
    }
}
