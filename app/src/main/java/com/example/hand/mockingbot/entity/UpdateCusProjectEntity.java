package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/22.
 */

public class UpdateCusProjectEntity {

    /**
     * msg : 操作成功
     * code : 100
     * data : [{"projectRisk":"0","projectStage":"第二阶段上线","code":"013","projectNo":"201702-04","overallProgress":"71","name":"综合项目管理平台","id":6,"beginTime":"2017-02-20","endTime":"2017-06-14","cpjlName":"王艺霖,孙宇","projectImprotance":"是","projectState":"正常"}]
     * page : {"page_no":1,"page_size":10,"total_pages":1,"total_elements":1,"number_elements":1}
     */

    private String msg;
    private String code;
    private PageBean page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        /**
         * page_no : 1
         * page_size : 10
         * total_pages : 1
         * total_elements : 1
         * number_elements : 1
         */

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

    public class DataBean {
        /**
         * projectRisk : 0
         * projectStage : 第二阶段上线
         * code : 013
         * projectNo : 201702-04
         * overallProgress : 71
         * name : 综合项目管理平台
         * id : 6
         * projectManager 项目经理
         * beginTime : 2017-02-20
         * endTime : 2017-06-14
         * cpjlName : 王艺霖,孙宇
         * projectImprotance : 是
         * projectState : 正常
         */

        private String projectRisk;
        private String projectStage;
        private String code;
        private String projectNo;
        private String overallProgress;
        private String name;
        private int id;
        private String projectManager;
        private String beginTime;
        private String endTime;
        private String cpjlName;
        private String projectImprotance;
        private String projectState;

        public String getProjectRisk() {
            return projectRisk;
        }

        public void setProjectRisk(String projectRisk) {
            this.projectRisk = projectRisk;
        }

        public String getProjectManager() {
            return projectManager;
        }

        public void setProjectManager(String projectManager) {
            this.projectManager = projectManager;
        }

        public String getProjectStage() {
            return projectStage;
        }

        public void setProjectStage(String projectStage) {
            this.projectStage = projectStage;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getProjectNo() {
            return projectNo;
        }

        public void setProjectNo(String projectNo) {
            this.projectNo = projectNo;
        }

        public String getOverallProgress() {
            return overallProgress;
        }

        public void setOverallProgress(String overallProgress) {
            this.overallProgress = overallProgress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCpjlName() {
            return cpjlName;
        }

        public void setCpjlName(String cpjlName) {
            this.cpjlName = cpjlName;
        }

        public String getProjectImprotance() {
            return projectImprotance;
        }

        public void setProjectImprotance(String projectImprotance) {
            this.projectImprotance = projectImprotance;
        }

        public String getProjectState() {
            return projectState;
        }

        public void setProjectState(String projectState) {
            this.projectState = projectState;
        }
    }
}
