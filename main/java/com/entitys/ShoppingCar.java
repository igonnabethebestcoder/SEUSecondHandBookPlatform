package com.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ShoppingCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "userid")
    private Integer userid;

    @Column(name = "bookid")
    private Integer bookid;

    public ShoppingCar(){}

    /**
     * @return Integer return the userid
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return Integer return the bookid
     */
    public Integer getBookid() {
        return bookid;
    }

    /**
     * @param bookid the bookid to set
     */
    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

}
