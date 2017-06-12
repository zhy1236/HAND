package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/7.
 */

public class ResourcePersonEntity {

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

        private int del_flag;
        private int project;
        private int days;
        private String position;
        private int id;
        private String account;
        private String realname;
        private List<Integer> workday;

        public int getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(int del_flag) {
            this.del_flag = del_flag;
        }

        public int getProject() {
            return project;
        }

        public void setProject(int project) {
            this.project = project;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public List<Integer> getWorkday() {
            return workday;
        }

        public void setWorkday(List<Integer> workday) {
            this.workday = workday;
        }
    }
}
