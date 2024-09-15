package com.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UpdateBookController {
    @Autowired
    private FileStorageServer fileStorageServer;
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private LableServer lableServer;
    @Autowired
    UserLibraryServer userLibraryServer;
    public UpdateBookController(BookLibraryServer bookLibraryServer, LableServer lableServer)
    {
        this.bookLibraryServer = bookLibraryServer;
        this.lableServer = lableServer;
    }

    @RequestMapping("/user/updateBook/{id}")
    public String jumpToUpdatePage(@PathVariable("id") Integer id, HttpServletRequest request)
    {
        Book book = bookLibraryServer.getBookById(id);
        request.setAttribute("book", book);

        List<Lable> lables = lableServer.getAllLables();  
        request.setAttribute("SubLabels", lables);

        return "user/updateBookPage";
    }


    @RequestMapping("/user/updateBook/{id}/onsubmit")
    public ResponseEntity<?> onSubmit(@PathVariable("id") Integer id, 
                            @RequestParam("bookName") String bookName,  
                            @RequestParam("author") String author,  
                            @RequestParam("publisher") String publisher,  
                            @RequestParam("bookLable") String bookLabel,
                            @RequestParam("price") float price,  
                            @RequestParam("details") String details,
                            @RequestParam("img") MultipartFile file
                            )//@RequestParam("img") MultipartFile file,
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

        Book oldBook = bookLibraryServer.getBookById(id);
        Book book = new Book();
        book.setId(id);
        book.setBookname(bookName);
        book.setWriter(author);
        book.setPublisher(publisher);
        book.setDetails(details);
        book.setPrice(price);
        book.setLable(bookLabel);
        book.setBuyerid(0);
        book.setOwnerid(user.getId());
        book.setImg(oldBook.getImg());

        if (!file.isEmpty()) {
            // 删除旧图片
            if (oldBook != null && oldBook.getImg() != null) {
                fileStorageServer.deleteFile(oldBook.getImg());
            }

            String fileName = fileStorageServer.storeFile(file);
            book.setImg(fileName);  // 保存文件名到数据库
        }
        
        //flag判断书籍是否上传成功
        boolean flag = bookLibraryServer.submitOldBook(book);
        if (flag) {
            return new ResponseEntity<>("书籍更新成功", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("书籍更新失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //删除我的书籍
    @RequestMapping("/user/deletebook/{id}")
    @ResponseBody
    public boolean deleteBookById(@PathVariable("id") Integer id)
    {
        boolean flag = bookLibraryServer.deleteBookById(id);
        return flag;
    }
}
