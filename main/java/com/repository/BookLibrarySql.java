package com.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entitys.Book;
@Repository//用于进行数据库操作
public interface BookLibrarySql extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book>{
    // 可以在需要的情况下添加自定义的查询方法
    
    Optional<Book> findById(Integer id);

    // @Query("SELECT b FROM Book b " +  
    //     "WHERE b.issoldout = 0 AND b.userid != :userid AND"+
    //             "(:content IS NULL OR " +  
    //             "(b.bookname LIKE CONCAT('%', :content, '%') OR " +  
    //              "b.writer LIKE CONCAT('%', :content, '%') OR " +  
    //              "b.publisher LIKE CONCAT('%', :content, '%') OR " +  
    //              "b.lable LIKE CONCAT('%', :content, '%')))")  
    // List<Book> searchBooksByContent(@Param("content") String content, @Param("userid") Integer userid);  

    //基础模糊搜索
    @Query("SELECT b FROM Book b " +  
       "WHERE b.issoldout = 0 AND b.ownerid != :userid AND " +
             "(:content IS NULL OR " +
             "(b.bookname LIKE %:content% OR " +
             "b.writer LIKE %:content% OR " +
             "b.publisher LIKE %:content% OR " +
             "b.lable LIKE %:content%))")
    List<Book> searchBooksByContent(@Param("content") String content, @Param("userid") Integer userid);


    //高级搜索
    @Query("SELECT b FROM Book b " +
           "WHERE b.issoldout = 0 AND b.ownerid != :userid AND " +
           "(:bookName IS NULL OR b.bookname LIKE %:bookName%) AND " +
           "(:author IS NULL OR b.writer LIKE %:author%) AND " +
           "(:publisher IS NULL OR b.publisher LIKE %:publisher%) AND " +
           "(:label IS NULL OR b.lable LIKE %:label%)")
    List<Book> advanceSearch(@Param("bookName") String bookName,
                                   @Param("author") String author,
                                   @Param("publisher") String publisher,
                                   @Param("label") String label,
                                   @Param("userid") Integer userid);

    //上传书籍
    //有jpa原生的save()方法，save()同样可以用于更新书籍

    //删除书籍 
    void deleteById(Integer id);

    //获取全部没售卖的书籍
    @Query("SELECT b FROM Book b WHERE b.issoldout = 0 AND b.ownerid != :userid")  
    List<Book> getBooksNotSoldOut(@Param("userid") Integer userid);

    //获取owner的未售卖的书籍
    @Query("SELECT b FROM Book b WHERE b.issoldout = 0 AND b.ownerid = :ownerid")
    List<Book> getBooksNotSoldByOwnerid(@Param("ownerid") Integer ownerid);
    
    //获取owner已售卖的书籍
    @Query("SELECT b FROM Book b WHERE b.issoldout = 1 AND b.ownerid = :ownerid")
    List<Book> getBooksHasSoldByOwnerid(@Param("ownerid") Integer ownerid);

    // 例如，根据书名查询书籍信息
    List<Book> findByBookname(String bookName);
    
    // 例如，根据作者查询书籍信息
    List<Book> findByWriter(String writer);
    
    // 例如，根据出版商查询书籍信息
    List<Book> findByPublisher(String publisher);

    List<Book> findByLable(String lable);
    
    // 使用分页查询获取书籍列表  
    //Page<Book> findByBookIDOrderByBookIDAsc(Pageable pageable);

    List<Book> findAll();
    
    //使用于我的上传页面
    List<Book> findByOwnerid(Integer ownerid);

    //查询当前有多少本书，用于mainPage整体展示,自带的接口
    long count();


    //查询where用户id，有多少本书，用于PC页展示
    // @Query("SELECT COUNT(b) FROM Book b WHERE b.ownerID = :ownerid")  
    // long countByOwnerid(@Param("ownerid") String ownerid);

    //
    // @Query("SELECT b FROM book b WHERE b.bookID = :bookID")
    // Book selectBookByID(@Param("bookID") String bookID);
    //insert添加书籍
}
