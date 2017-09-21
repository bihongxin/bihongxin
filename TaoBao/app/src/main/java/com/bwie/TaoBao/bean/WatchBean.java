package com.bwie.TaoBao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/18 14:43
 */
@Entity
public class WatchBean {
    @Id
    private Long id;
    private String goods_name;
    private String url;
    @Generated(hash = 2060254145)
    public WatchBean(Long id, String goods_name, String url) {
        this.id = id;
        this.goods_name = goods_name;
        this.url = url;
    }
    @Generated(hash = 1265525280)
    public WatchBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGoods_name() {
        return this.goods_name;
    }
    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
