package com.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entitys.Book;
import com.entitys.Lable;
import com.repository.LableSql;

@Service
public class LableServer {
    @Autowired
    LableSql lableSql;
    @Autowired
    BookLibraryServer bookLibraryServer;
    public LableServer(LableSql lableSql)
    {
        this.lableSql = lableSql;
    }

    public List<Lable> getAllLables()
    {
        List<Lable> lableList = lableSql.findAll();
        return lableList;
    }

    public boolean checkLableDuplicated(String lable)
    {
        List<Lable> lables = lableSql.findByLable(lable);
        if (lables.size() == 0)
            return false;
        return true;
    }

    public int addLable(String lable)
    {
        Lable newlable = new Lable();
        newlable.setLable(lable);
        
        //查重
        boolean flag = this.checkLableDuplicated(lable);
        if (flag)
        {
            System.out.println("已存在");
            return -1;//已经存在
        }
            

        //保存
        lableSql.save(newlable);

        //查看是否保存成功
        flag = this.checkLableDuplicated(lable);
        if (flag)
            return 1;//保存成功

        return 0;//保存失败
    }

    public boolean editLable(String oldlable, String newlable)
    {
        //更新lable表
        List<Lable> lablelist = lableSql.findByLable(oldlable);
        Lable lable = lablelist.get(0);
        if (lable != null)
        {
            lable.setLable(newlable);
            lableSql.save(lable);
            List<Lable> list = lableSql.findByLable(newlable);
            if (list.size() == 0)
                return false;

            //更新所有为oldlable的书籍的lable
            List<Book> booklist = bookLibraryServer.getBooksByLable(oldlable);
            for(int i = 0; i < booklist.size();i++)
            {
                Book book = booklist.get(i);
                book.setLable(newlable);
                bookLibraryServer.updateBookInfo(book);
            }
            return true;
        }
        return false;
    }

    public Lable getLableById(Integer oldlableid) {
        Optional<Lable> lable = lableSql.findById(oldlableid);
        Lable a = lable.orElse(null);
        return a;
    }

}
