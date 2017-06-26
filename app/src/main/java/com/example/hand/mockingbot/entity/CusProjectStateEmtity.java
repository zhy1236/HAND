package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/22.
 */

public class CusProjectStateEmtity {

    /**
     * msg : 操作成功
     * code : 100
     * data : [{"state":"正常"},{"state":"延期"},{"state":"结束"},{"state":"关闭"}]
     */

    private String msg;
    private String code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean {
        /**
         * state : 正常
         */

        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
