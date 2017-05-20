package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/19.
 */

public class GetDailyStatisticalEntity {

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
             * identifying : receivedDaily
             * moduleName : 收到日报
             * isNotNum : 1
             * imgAddress : /img/dailyImg/receivedDaily.png
             * urlAddress : daily/selectAllDaily
             */

            private String identifying;
            private String moduleName;
            private int isNotNum;
            private String imgAddress;
            private String urlAddress;

            public String getIdentifying() {
                return identifying;
            }

            public void setIdentifying(String identifying) {
                this.identifying = identifying;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public int getIsNotNum() {
                return isNotNum;
            }

            public void setIsNotNum(int isNotNum) {
                this.isNotNum = isNotNum;
            }

            public String getImgAddress() {
                return imgAddress;
            }

            public void setImgAddress(String imgAddress) {
                this.imgAddress = imgAddress;
            }

            public String getUrlAddress() {
                return urlAddress;
            }

            public void setUrlAddress(String urlAddress) {
                this.urlAddress = urlAddress;
            }
        }
    }
}
