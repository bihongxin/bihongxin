package com.bwie.TaoBao.bean;

import java.io.Serializable;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/14 14:15
 */

public class ChildBean implements Serializable{
    private boolean c_ischecked;
    private String c_image;
    private String c_name;
    private String c_price;
    private String c_number;
    private String c_cardid;

    public ChildBean(boolean c_ischecked, String c_image, String c_name, String c_price, String c_number, String c_cardid) {
        this.c_ischecked = c_ischecked;
        this.c_image = c_image;
        this.c_name = c_name;
        this.c_price = c_price;
        this.c_number = c_number;
        this.c_cardid = c_cardid;
    }

    public ChildBean() {
    }

    public boolean isC_ischecked() {
        return c_ischecked;
    }

    public void setC_ischecked(boolean c_ischecked) {
        this.c_ischecked = c_ischecked;
    }

    public String getC_image() {
        return c_image;
    }

    public void setC_image(String c_image) {
        this.c_image = c_image;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_price() {
        return c_price;
    }

    public void setC_price(String c_price) {
        this.c_price = c_price;
    }

    public String getC_number() {
        return c_number;
    }

    public void setC_number(String c_number) {
        this.c_number = c_number;
    }

    public String getC_cardid() {
        return c_cardid;
    }

    public void setC_cardid(String c_cardid) {
        this.c_cardid = c_cardid;
    }

    @Override
    public String toString() {
        return "ChildBean{" +
                "c_ischecked=" + c_ischecked +
                ", c_image='" + c_image + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_price='" + c_price + '\'' +
                ", c_number='" + c_number + '\'' +
                ", c_cardid='" + c_cardid + '\'' +
                '}';
    }
}
