package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/22.
 */

public class CusProjectStageEntity {

    /**
     * msg : 操作成功
     * code : 100
     * data : [{"stage":"项目准备","id":269,"time":"2016-12-01","cusProductId":9},{"stage":"系统实现","id":272,"time":"2017-05-12","cusProductId":9},{"stage":"系统测试","id":273,"time":"2017-05-19","cusProductId":9}]
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
         * stage : 项目准备
         * id : 269
         * time : 2016-12-01
         * cusProductId : 9
         */

        private String stage;
        private int id;
        private String time;
        private int cusProductId;

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCusProductId() {
            return cusProductId;
        }

        public void setCusProductId(int cusProductId) {
            this.cusProductId = cusProductId;
        }
    }
}
