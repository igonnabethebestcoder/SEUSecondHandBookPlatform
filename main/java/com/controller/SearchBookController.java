package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entitys.Book;
import com.entitys.User;
import com.services.BookLibraryServer;
import com.services.UserLibraryServer;

@Controller
public class SearchBookController {
    @Autowired
    private BookLibraryServer bookLibraryServer;
    @Autowired
    private UserLibraryServer userLibraryServer;
    public SearchBookController(BookLibraryServer bookLibraryServer)
    {
        this.bookLibraryServer = bookLibraryServer;
    }

    @RequestMapping("/common/searchBook")
    public String searchBookByInput(HttpServletRequest request, @RequestParam("searchContent") String content)
    {
        User user = userLibraryServer.getCurrentUserBySeucurity();
        if (user.getRole().equals("ROLE_user"))
            request.setAttribute("role", 1);
        else
            request.setAttribute("role", 2);
        List<Book> books = bookLibraryServer.getBookByContent(content);
        request.setAttribute("afterSearchBookList", books);
        request.setAttribute("content", content);
        
        return "common/searchPage";
    }

    @RequestMapping("/common/advanceSearch")
    public String advanceSearching(HttpServletRequest request, 
                                    @RequestParam(value = "bookName", required = false) String bookName,
                                    @RequestParam(value = "author", required = false) String author,
                                    @RequestParam(value = "publisher", required = false) String publisher,
                                    @RequestParam(value = "label", required = false) String label)
    {
        List<Book> books = bookLibraryServer.advanceSearch(bookName, author, publisher, label);
        request.setAttribute("afterSearchBookList", books);
        request.setAttribute("content", "高级搜索: "+bookName+"-"+author+"-"+publisher+"-"+label);
        return "common/searchPage";
    }



    @RequestMapping("/common/searchBook/{content}")
    public String searchBookByLableClick(HttpServletRequest request, @PathVariable("content") String content)
    {
        List<Book> books = bookLibraryServer.getBookByContent(content);
        request.setAttribute("afterSearchBookList", books);
        request.setAttribute("content", content);
        
        return "common/searchPage";
    }
}
