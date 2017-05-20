package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class JournalBean {

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

        private DataBean data;
        private int count;
        private List<ReaderBean> reader;
        private List<CommentBean> comment;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ReaderBean> getReader() {
            return reader;
        }

        public void setReader(List<ReaderBean> reader) {
            this.reader = reader;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public static class DataBean {

            private String dailyId;
            private String userId;
            private String finishWork;
            private String unfinishWork;
            private String coordinationWork;
            private String remark;
            private String enclosureUrl;
            private long submitDate;
            private long currentDate;
            private String isAfterPayment;

            public String getDailyId() {
                return dailyId;
            }

            public void setDailyId(String dailyId) {
                this.dailyId = dailyId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

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

            public String getCoordinationWork() {
                return coordinationWork;
            }

            public void setCoordinationWork(String coordinationWork) {
                this.coordinationWork = coordinationWork;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getEnclosureUrl() {
                return enclosureUrl;
            }

            public void setEnclosureUrl(String enclosureUrl) {
                this.enclosureUrl = enclosureUrl;
            }

            public long getSubmitDate() {
                return submitDate;
            }

            public void setSubmitDate(long submitDate) {
                this.submitDate = submitDate;
            }

            public long getCurrentDate() {
                return currentDate;
            }

            public void setCurrentDate(long currentDate) {
                this.currentDate = currentDate;
            }

            public String getIsAfterPayment() {
                return isAfterPayment;
            }

            public void setIsAfterPayment(String isAfterPayment) {
                this.isAfterPayment = isAfterPayment;
            }
        }

        public static class ReaderBean {

            private String realname;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
        }

        public static class CommentBean {

            private String commentDate;
            private int isReadFlag;
            private int commentId;
            private int dailyId;
            private int userId;
            private String content;
            private String realname;

            public String getCommentDate() {
                return commentDate;
            }

            public void setCommentDate(String commentDate) {
                this.commentDate = commentDate;
            }

            public int getIsReadFlag() {
                return isReadFlag;
            }

            public void setIsReadFlag(int isReadFlag) {
                this.isReadFlag = isReadFlag;
            }

            public int getCommentId() {
                return commentId;
            }

            public void setCommentId(int commentId) {
                this.commentId = commentId;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
        }
    }
}
