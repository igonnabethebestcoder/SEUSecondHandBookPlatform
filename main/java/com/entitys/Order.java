package com.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime; 

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="bookid")
    private Integer bookid;

    @Column(name="buyerid")
    private Integer buyerid;

    @Column(name="sellerid")
    private Integer sellerid;

    @Column(name = "happenedtime")
    private LocalDateTime happenedtime;

    @Column(name = "hasappeal", nullable = false, length = 1)
    private int hasappeal;

    @Column(name = "detail", nullable = true)
    private String detail;

    @Column(name="appealoutcome")
    private int appealoutcome;

    //0是待确认收货, 1是已收货
    @Column(name = "buyoutcome")
    private int buyoutcome;

    @Column(name = "haspay")//表示是否支付
    private int haspay;

    @Column(name="price")
    private float price;

    @Column(name="bookname")
    private String bookname;

    @Column(name="img")
    private String img;
    public Order(){}



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
     * @return Integer return the sellerid
     */
    public Integer getSellerid() {
        return sellerid;
    }

    /**
     * @param sellerid the sellerid to set
     */
    public void setSellerid(Integer sellerid) {
        this.sellerid = sellerid;
    }

    /**
     * @return LocalDateTime return the happenedtime
     */
    public LocalDateTime getHappenedtime() {
        return happenedtime;
    }

    /**
     * @param happenedtime the happenedtime to set
     */
    public void setHappenedtime(LocalDateTime happenedtime) {
        this.happenedtime = happenedtime;
    }

    /**
     * @return int return the hasappeal
     */
    public int getHasappeal() {
        return hasappeal;
    }

    /**
     * @param hasappeal the hasappeal to set
     */
    public void setHasappeal(int hasappeal) {
        this.hasappeal = hasappeal;
    }

    /**
     * @return String return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return int return the appealoutcome
     */
    public int getAppealoutcome() {
        return appealoutcome;
    }

    /**
     * @param appealoutcome the appealoutcome to set
     */
    public void setAppealoutcome(int appealoutcome) {
        this.appealoutcome = appealoutcome;
    }

    /**
     * @return int return the buyoutcome
     */
    public int getBuyoutcome() {
        return buyoutcome;
    }

    /**
     * @param buyoutcome the buyoutcome to set
     */
    public void setBuyoutcome(int buyoutcome) {
        this.buyoutcome = buyoutcome;
    }

    /**
     * @return int return the haspay
     */
    public int getHaspay() {
        return haspay;
    }

    /**
     * @param haspay the haspay to set
     */
    public void setHaspay(int haspay) {
        this.haspay = haspay;
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
