package com.ruan.usersystem.entity;

public class test {
    private Integer id;
    private Integer cid;

    // @NotEmpty
    private String title;
    private String pro_no;
    private String keywords;
    private String img;
    private Float price;
    private String desc;
    private Integer pv;
    private Integer status;
    private Integer add_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPro_no() {
        return pro_no;
    }

    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Integer add_time) {
        this.add_time = add_time;
    }
}
