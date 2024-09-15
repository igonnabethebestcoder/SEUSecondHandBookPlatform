package com;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication  
public class SpringBootDemoApplication {  
  
    public static void main(String[] args) {  
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }  
        SpringApplication.run(SpringBootDemoApplication.class, args);  
    }  
}