package com.nju.authorization;

import java.io.Serializable;

/**
 * Created by xiaojuzhang on 2015/11/12.
 */
public class UserInfo {

    private String label;
    private String name;
    private String sex;
    private String yuanXiaoName;
    private String fenYuan;
    private String major;
    private String date;
    public UserInfo(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYuanXiaoName() {
        return yuanXiaoName;
    }

    public void setYuanXiaoName(String yuanXiaoName) {
        this.yuanXiaoName = yuanXiaoName;
    }

    public String getFenYuan() {
        return fenYuan;
    }

    public void setFenYuan(String fenYuan) {
        this.fenYuan = fenYuan;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
