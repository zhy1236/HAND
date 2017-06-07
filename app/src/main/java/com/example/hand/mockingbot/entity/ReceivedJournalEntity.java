package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/17.
 */

public class ReceivedJournalEntity {

    private ResultBean result;
    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private PageBean page;
        private List<DataBean> data;

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
             * total_pages : 19
             * total_elements : 188
             * number_elements : 10
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

            private String finishWork;
            private String unfinishWork;
            private String isReadFlag;
            private long submitDate;
            private String coordinationWork;
            private String focus;
            private String remark;
            private int dailyId;
            private int userId;
            private String value;
            private String commentCount;
            private String realname;
            private String enclosureUrl;

            public String getFinishWork() {
                return finishWork;
            }

            public void setFinishWork(String finishWork) {
                this.finishWork = finishWork;
            }

            public String getUnfinishWork() {
                return unfinishWork;
            }

            public void setUnfinishWork(String unfinishWork) {
                this.unfinishWork = unfinishWork;
            }

            public String getIsReadFlag() {
                return isReadFlag;
            }

            public void setIsReadFlag(String isReadFlag) {
                this.isReadFlag = isReadFlag;
            }

            public long getSubmitDate() {
                return submitDate;
            }

            public void setSubmitDate(long submitDate) {
                this.submitDate = submitDate;
            }

            public String getCoordinationWork() {
                return coordinationWork;
            }

            public void setCoordinationWork(String coordinationWork) {
                this.coordinationWork = coordinationWork;
            }

            public String getFocus() {
                return focus;
            }

            public void setFocus(String focus) {
                this.focus = focus;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getDailyId() {
                return dailyId;
            }

            public void setDailyId(int dailyId) {
                this.dailyId = dailyId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getEnclosureUrl() {
                return enclosureUrl;
            }

            public void setEnclosureUrl(String enclosureUrl) {
                this.enclosureUrl = enclosureUrl;
            }
        }
    }
}
