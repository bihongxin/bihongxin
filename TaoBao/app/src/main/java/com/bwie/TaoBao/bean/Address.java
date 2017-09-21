package com.bwie.TaoBao.bean;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/15 20:04
 */

public class Address {

    /**
     * code : 200
     * datas : {"address_list":[{"address_id":"11","member_id":"4","true_name":"张三","area_id":"0","city_id":"0","area_info":"鸟巢脚下","address":"奥林匹克公园","tel_phone":null,"mob_phone":"15011478123","is_default":"0","dlyp_id":"0"},{"address_id":"10","member_id":"4","true_name":"刘畅","area_id":"1","city_id":"1","area_info":"马连道北路","address":"西城区建行","tel_phone":null,"mob_phone":"17777811413","is_default":"0","dlyp_id":"0"},{"address_id":"9","member_id":"4","true_name":"毕洪鑫","area_id":"1","city_id":"1","area_info":"首都","address":"海淀区","tel_phone":null,"mob_phone":"15011478731","is_default":"0","dlyp_id":"0"},{"address_id":"4","member_id":"4","true_name":"毕洪鑫","area_id":"2","city_id":"1","area_info":"北京八维研修学院","address":"八维移动","tel_phone":null,"mob_phone":"12345567811","is_default":"0","dlyp_id":"0"}]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private List<AddressListBean> address_list;

        public List<AddressListBean> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<AddressListBean> address_list) {
            this.address_list = address_list;
        }

        public static class AddressListBean {
            /**
             * address_id : 11
             * member_id : 4
             * true_name : 张三
             * area_id : 0
             * city_id : 0
             * area_info : 鸟巢脚下
             * address : 奥林匹克公园
             * tel_phone : null
             * mob_phone : 15011478123
             * is_default : 0
             * dlyp_id : 0
             */

            private String address_id;
            private String member_id;
            private String true_name;
            private String area_id;
            private String city_id;
            private String area_info;
            private String address;
            private Object tel_phone;
            private String mob_phone;
            private String is_default;
            private String dlyp_id;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public Object getTel_phone() {
                return tel_phone;
            }

            public void setTel_phone(Object tel_phone) {
                this.tel_phone = tel_phone;
            }

            public String getMob_phone() {
                return mob_phone;
            }

            public void setMob_phone(String mob_phone) {
                this.mob_phone = mob_phone;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getDlyp_id() {
                return dlyp_id;
            }

            public void setDlyp_id(String dlyp_id) {
                this.dlyp_id = dlyp_id;
            }
        }
    }
}
