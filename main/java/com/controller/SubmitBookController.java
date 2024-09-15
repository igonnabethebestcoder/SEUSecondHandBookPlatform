package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entitys.Book;
import com.entitys.Lable;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.FileStorageServer;
import com.services.LableServer;
import com.services.UserLibraryServer;


@Controller
public class SubmitBookController {
    @Autowired
    private FileStorageServer fileStorageServer;
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private LableServer lableServer;
    @Autowired
    private UserLibraryServer userLibraryServer;
    public SubmitBookController(BookLibraryServer bookLibraryServer, LableServer lableServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.lableServer = lableServer;
    }

    @RequestMapping("/user/submitbook")
    public String jumpToSubmitPage(HttpServletRequest request)
    {
        List<Lable> lables = lableServer.getAllLables();
        request.setAttribute("SubLables", lables);
        return "user/submitOldBook";//先跳转，在刷新
    }

    @RequestMapping("/user/submitbook/loadLables")
    @ResponseBody
    public List<String> loadLabels() {  
        
        List<Lable> lables = lableServer.getAllLables();  
        List<String> sLables = new ArrayList<>();
        for (int i =0 ; i < lables.size(); i++)
        {
            sLables.add(lables.get(i).getLable());
            //System.out.println(sLables.get(i));
        }
        return sLables;  
    }


    @RequestMapping("/user/submitbook/onsubmit")
    public ResponseEntity<?> onSubmit(@RequestParam("bookName") String bookName,  
                            @RequestParam("author") String author,  
                            @RequestParam("publisher") String publisher,  
                            @RequestParam("bookLable") String bookLabel,
                            @RequestParam("price") float price,  
                            @RequestParam("details") String details,
                            @RequestParam("img") MultipartFile file
                            )
    {
        //获取用户信息
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null; // 初始化为null  
        try {  
            user = userLibraryServer.getUserByUsername(userName);  
            System.out.println(user);
        } catch (Exception e) {  
            e.printStackTrace();   
        }

        Book book = new Book();
        book.setBookname(bookName);
        book.setWriter(author);
        book.setPublisher(publisher);
        book.setDetails(details);
        book.setPrice(price);
        book.setLable(bookLabel);
        book.setBuyerid(0);
        book.setOwnerid(user.getId());
        if (!file.isEmpty()) {
            String fileName = fileStorageServer.storeFile(file);
            book.setImg(fileName);  // 保存文件名到数据库
        }
        
        //flag判断书籍是否上传成功
        boolean flag = bookLibraryServer.submitOldBook(book);
        if (flag) {
            return new ResponseEntity<>("书籍上传成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("书籍上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
