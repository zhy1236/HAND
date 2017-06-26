package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/23.
 */

public class RiskListEntity {
    /**
     * msg : 操作成功
     * code : 100
     * data : [{"handler":107,"issueTypeName":"需求","issueId":8,"modifier":107,"creatDate":"2017-06-22","realname":"韩西德","issueType":"1","deleteFlag":"0","modifierDate":"2017-06-22","projectNo":"012","solution":"12121211231321111","stateName":"已完成","issueDesc":"1111122221312恶趣味去","creater":"韩西德","actualDate":1498114108000,"state":"1","modifierName":"韩西德"}]
     * page : {"page_no":1,"page_size":10,"total_pages":1,"total_elements":5,"number_elements":5}
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
         * total_elements : 5
         * number_elements : 5
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

    public static class DataBean {
        /**
         * handler : 107
         * issueTypeName : 需求
         * issueId : 8
         * modifier : 107
         * creatDate : 2017-06-22
         * realname : 韩西德
         * issueType : 1
         * deleteFlag : 0
         * modifierDate : 2017-06-22
         * projectNo : 012
         * solution : 12121211231321111
         * stateName : 已完成
         * issueDesc : 1111122221312恶趣味去
         * creater : 韩西德
         * actualDate : 1498114108000
         * state : 1
         * modifierName : 韩西德
         */

        private int handler;
        private String issueTypeName;
        private int issueId;
        private int modifier;
        private String creatDate;
        private String realname;
        private String issueType;
        private String deleteFlag;
        private String modifierDate;
        private String projectNo;
        private String solution;
        private String stateName;
        private String issueDesc;
        private String creater;
        private long actualDate;
        private String state;
        private String modifierName;

        public int getHandler() {
            return handler;
        }

        public void setHandler(int handler) {
            this.handler = handler;
        }

        public String getIssueTypeName() {
            return issueTypeName;
        }

        public void setIssueTypeName(String issueTypeName) {
            this.issueTypeName = issueTypeName;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public int getModifier() {
            return modifier;
        }

        public void setModifier(int modifier) {
            this.modifier = modifier;
        }

        public String getCreatDate() {
            return creatDate;
        }

        public void setCreatDate(String creatDate) {
            this.creatDate = creatDate;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIssueType() {
            return issueType;
        }

        public void setIssueType(String issueType) {
            this.issueType = issueType;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public String getModifierDate() {
            return modifierDate;
        }

        public void setModifierDate(String modifierDate) {
            this.modifierDate = modifierDate;
        }

        public String getProjectNo() {
            return projectNo;
        }

        public void setProjectNo(String projectNo) {
            this.projectNo = projectNo;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getIssueDesc() {
            return issueDesc;
        }

        public void setIssueDesc(String issueDesc) {
            this.issueDesc = issueDesc;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public long getActualDate() {
            return actualDate;
        }

        public void setActualDate(long actualDate) {
            this.actualDate = actualDate;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getModifierName() {
            return modifierName;
        }

        public void setModifierName(String modifierName) {
            this.modifierName = modifierName;
        }
    }
}
