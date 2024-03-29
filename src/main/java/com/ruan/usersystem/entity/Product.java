package com.ruan.usersystem.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
// import javax.validation.constraints.NotEmpty;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cid;

    // @NotEmpty
    private String title;
    private String pro_no;
    private String keywords;
    private String img;
    private BigDecimal price;
    private String desc;
    private Integer pv;
    private Integer status;
    private Long add_time;

    public Product() {};

    /**
     * 获取Id
     * */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * 获取Id
     * */
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取Id
     * */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取Id
     * */
    public String getPro_no() {
        return pro_no;
    }

    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }

    /**
     * 获取Id
     * */
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 获取Id
     * */
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取Id
     * */
    public BigDecimal getPrice() {
        return price;
    }

    public void setImg(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取Id
     * */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取Id
     * */
    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    /**
     * 获取Id
     * */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取Id
     * */
    public Long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Long add_time) {
        this.add_time = add_time;
    }
}
