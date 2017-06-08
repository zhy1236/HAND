package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/6/8.
 */

public class ResultEntity {
    /**
     * result : {"data":"1"}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : 1
         */

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
