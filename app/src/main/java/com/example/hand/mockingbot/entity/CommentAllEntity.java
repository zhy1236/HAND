package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/25.
 */

public class CommentAllEntity {

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
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * commentDate : 2017-05-24 11:35:07
             * isReadFlag : 1
             * submitDate : 2017-05-24
             * dailyId : 375
             * content : 刷新功能测试
             * realname : 周海宇
             */

            private String commentDate;
            private int isReadFlag;
            private String submitDate;
            private int dailyId;
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

            public String getSubmitDate() {
                return submitDate;
            }

            public void setSubmitDate(String submitDate) {
                this.submitDate = submitDate;
            }

            public int getDailyId() {
                return dailyId;
            }

            public void setDailyId(int dailyId) {
                this.dailyId = dailyId;
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
