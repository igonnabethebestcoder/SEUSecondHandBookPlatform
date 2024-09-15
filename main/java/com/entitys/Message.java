package com.entitys;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bookid")
    private Integer bookid;

    @Column(name = "tousername")
    private String tousername;

    @Column(name = "fromusername")
    private String fromusername;

    @Column(name = "msg")
    private String msg;

    @Column(name = "time")
    private LocalDateTime time;

    public Message(){}

    /**
     * @return Integer return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
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
     * @return String return the tousername
     */
    public String getTousername() {
        return tousername;
    }

    /**
     * @param tousername the tousername to set
     */
    public void setTousername(String tousername) {
        this.tousername = tousername;
    }

    /**
     * @return String return the fromusername
     */
    public String getFromusername() {
        return fromusername;
    }

    /**
     * @param fromusername the fromusername to set
     */
    public void setFromusername(String fromusername) {
        this.fromusername = fromusername;
    }

    /**
     * @return String return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return LocalDateTime return the time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
