package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.entitys.Book;
import com.entitys.User;
import com.repository.BookLibrarySql;
import java.util.Optional;
/**
 * BookLibraryServer
 */
@Service
public class BookLibraryServer {
    @Autowired
    private UserLibraryServer userLibraryServer;
    @Autowired
    private final BookLibrarySql bookLibrarySql;
    
    public BookLibraryServer(BookLibrarySql bookLibrarySql)
    {
        this.bookLibrarySql = bookLibrarySql;
    }

   
    public List<Book> getAllBooks()
    {
        return bookLibrarySql.findAll();
    }

     //主界面书籍全部展示
    public List<Book> getBooksNotSoldOut()
    {
        User user = userLibraryServer.getCurrentUserBySeucurity();

        return bookLibrarySql.getBooksNotSoldOut(user.getId());
    }

    //书籍详情提供
    public Book getBookById(Integer id)
    {
        Optional<Book> optionalBook = bookLibrarySql.findById(id);
        Book book = optionalBook.orElse(new Book("抱歉！书籍信息走丢了", -1));
        return book;
    }


    //上传书籍
    public boolean submitOldBook(Book book)
    {
        bookLibrarySql.save(book);
        //需要返回上传是否成功的消息
        return true;
    }

    //根据用户id获取书籍
    public List<Book> getBookListByOwnerid(Integer ownerid)
    {
        List<Book> bookList = bookLibrarySql.findByOwnerid(ownerid);
        return bookList;
    }


    //通过ownerid获取用户还未售出的书籍
    public List<Book> getNotSoldBookListByOwnerid(Integer ownerid)
    {
        List<Book> booklist = bookLibrarySql.getBooksNotSoldByOwnerid(ownerid);
        return booklist;
    }

    //通过ownerid获取用户已经售出的书籍
    public List<Book> getHasSoldBookListByOwnerid(Integer ownerid)
    {
        List<Book> booklist = bookLibrarySql.getBooksHasSoldByOwnerid(ownerid);
        return booklist;
    }


    //更新书籍信息
    public boolean updateBookInfo(Book book)
    {
        bookLibrarySql.save(book);
        return true;
    }

    //删除书籍
    public boolean deleteBookById(Integer id)
    {
        bookLibrarySql.deleteById(id);

        // 查看是否被删除  
        boolean flag = !bookLibrarySql.findById(id).isPresent();//isPresent()用于判断Optional<>是否为空
        return flag;
    }

    
    //通过搜索框内容获取书籍
    public List<Book> getBookByContent(String content)
    {
        User user = userLibraryServer.getCurrentUserBySeucurity();
        return bookLibrarySql.searchBooksByContent(content, user.getId());
    }
    // public List<Book> getBooks(int page)
    // {
    //     //使用分页来避免重复查询
    //     int pageSize = 10;
    //     // 创建Pageable对象  
    //     Pageable pageable = PageRequest.of(page - 1, pageSize); // page - 1 是因为PageRequest中的页码是从0开始的  
    //     // 执行查询  
    //     //Page<Book> booksPage = bookLibrarySql.findByBookIDOrderByBookIDAsc(pageable);  
    //     // 返回当前页的记录列表  
    //     return booksPage.getContent();
    // }


    //用于管理员更改书籍标签的时候的全局更改
    public List<Book> getBooksByLable(String newlable) {
        List<Book> booklist = bookLibrarySql.findByLable(newlable);
        return booklist;
    }


    //书籍的高级搜索
    public List<Book> advanceSearch(String bookName, String author, String publisher, String label) {
        User user = userLibraryServer.getCurrentUserBySeucurity();
        if (user == null)
            return null;
        List<Book> books = bookLibrarySql.advanceSearch(bookName, author, publisher, label, user.getId());
        return books;
    }
}