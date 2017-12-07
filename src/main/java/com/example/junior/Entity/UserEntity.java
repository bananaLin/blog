package com.example.junior.Entity;

import java.util.Date;

public class UserEntity {

    private int id;
    private String name;
    private String password;
    private Date createTime;
    private Date modifyTime;
    private String mail;
    private String phone;
    private Boolean isCheck;
    private Boolean isSend;

    public Boolean getIsSend()
    {
        return isSend;
    }

    public void setIsSend(Boolean isSend)
    {
        this.isSend = isSend;
    }

    public Boolean getIsCheck()
    {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck)
    {
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", mail='" + mail + '\'' +
                ", phone='" + phone + '\'' +
                ", isCheck= '" + isCheck + '\'' +
                '}';
    }
}
