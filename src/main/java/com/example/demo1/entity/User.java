package com.example.demo1.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
// import javax.validation.constraints.NotEmpty;


@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    // @NotEmpty
    private String account;
    private String password;
    private String name;
    private String detailAddress;
    private String email;
    public User() {};
    public User(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
    }
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
     * 获取账号
     * */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取用户名
     * */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 获取用户名
     * */
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * 获取详细地址
     * */
    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    /**
     * 获取邮件地址
     * */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "user{name='"+name+"\',"+"password="+password+"}";
    }

}
