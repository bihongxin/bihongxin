package com.bwie.TaoBao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/14 13:52
 */

public class GroupBean implements Serializable{
    private boolean g_ischecked;
    private String group_name;
    private List<ChildBean> clist;

    public GroupBean(boolean g_ischecked, String group_name, List<ChildBean> clist) {
        this.g_ischecked = g_ischecked;
        this.group_name = group_name;
        this.clist = clist;
    }

    public GroupBean() {
    }

    public boolean isG_ischecked() {
        return g_ischecked;
    }

    public void setG_ischecked(boolean g_ischecked) {
        this.g_ischecked = g_ischecked;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<ChildBean> getClist() {
        return clist;
    }

    public void setClist(List<ChildBean> clist) {
        this.clist = clist;
    }

    @Override
    public String toString() {
        return "GroupBean{" +
                "g_ischecked=" + g_ischecked +
                ", group_name='" + group_name + '\'' +
                ", clist=" + clist +
                '}';
    }
}
