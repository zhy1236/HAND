package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/5/18.
 */

public class AddJournalEntity {
    /**
     * result : {"data":"1","startTime":"2017-05-18","endTime":"2017-05-18","userId":"116"}
     */

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

        private String data;
        private String startTime;
        private String endTime;
        private String userId;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
