package com.entitys;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "book")
public class Book {
    //没有重写equals()，hashCode()
    //需要复合主键类
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  
  
    @Column(name = "ownerid")  
    private Integer ownerid;  

    @Column(name = "bookname")
    private String bookname;

    @Column(name = "price", precision = 2)
    private float price;

    @Column(name = "lable", nullable = true, length = 10)
    private String lable;

    @Column(name = "details", nullable = true)
    private String details;

    @Column(name = "issoldout", nullable = true, length = 1)
    private int issoldout;

    @Column(name = "isproblem", nullable = true, length = 1)
    private int isproblem;

    @Column(name = "writer", nullable = true, length = 30)
    private String writer;

    @Column(name = "publisher", nullable = true, length = 30)
    private String publisher;

    @Column(name="buyerid", nullable = false, length = 8)
    private Integer buyerid;

    @Column(name = "img", nullable = true, length = 100)
    private String img;

    public Book(){}

    public Book(String bookName, Integer id)
    {
        this.bookname = bookName;
        this.id = id;
    }
    

    
    

    /**
     * @return String return the bookname
     */
    public String getBookname() {
        return bookname;
    }

    /**
     * @param bookname the bookname to set
     */
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    /**
     * @return float return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return String return the lable
     */
    public String getLable() {
        return lable;
    }

    /**
     * @param lable the lable to set
     */
    public void setLable(String lable) {
        this.lable = lable;
    }

    /**
     * @return String return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return int return the issoldout
     */
    public int getIssoldout() {
        return issoldout;
    }

    /**
     * @param issoldout the issoldout to set
     */
    public void setIssoldout(int issoldout) {
        this.issoldout = issoldout;
    }

    /**
     * @return int return the isproblem
     */
    public int getIsproblem() {
        return isproblem;
    }

    /**
     * @param isproblem the isproblem to set
     */
    public void setIsproblem(int isproblem) {
        this.isproblem = isproblem;
    }


    /**
     * @return String return the writer
     */
    public String getWriter() {
        return writer;
    }

    /**
     * @param writer the writer to set
     */
    public void setWriter(String writer) {
        this.writer = writer;
    }

    /**
     * @return String return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


   


    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return Integer return the ownerid
     */
    public Integer getOwnerid() {
        return ownerid;
    }

    /**
     * @param ownerid the ownerid to set
     */
    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    /**
     * @return Integer return the buyerid
     */
    public Integer getBuyerid() {
        return buyerid;
    }

    /**
     * @param buyerid the buyerid to set
     */
    public void setBuyerid(Integer buyerid) {
        this.buyerid = buyerid;
    }


    /**
     * @return String return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(String img) {
        this.img = img;
    }

}
