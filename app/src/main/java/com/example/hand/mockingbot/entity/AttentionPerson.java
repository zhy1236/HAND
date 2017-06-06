package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/11.
 */

public class AttentionPerson {

    private String msg;
    private int code;
    private PageBean page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {

        private int page_no;
        private int page_size;
        private int total_pages;
        private int total_elements;
        private int number_elements;

        public int getPage_no() {
            return page_no;
        }

        public void setPage_no(int page_no) {
            this.page_no = page_no;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public int getTotal_elements() {
            return total_elements;
        }

        public void setTotal_elements(int total_elements) {
            this.total_elements = total_elements;
        }

        public int getNumber_elements() {
            return number_elements;
        }

        public void setNumber_elements(int number_elements) {
            this.number_elements = number_elements;
        }
    }

    public static class DataBean {

        private int managerId;
        private int memberId;
        private String realname;

        public int getManagerId() {
            return managerId;
        }

        public void setManagerId(int managerId) {
            this.managerId = managerId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
