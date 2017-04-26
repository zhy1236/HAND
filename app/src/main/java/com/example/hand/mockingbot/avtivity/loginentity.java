package com.example.hand.mockingbot.avtivity;

/**
 * Created by zhy on 2017/4/25.
 */

public class loginentity {
    /**
     * data : {"password":"e10adc3949ba59abbe56e057f20f883e","id":115,"account":"dongdong.ding","authPermission":0,"realname":"丁冬冬"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * password : e10adc3949ba59abbe56e057f20f883e
         * id : 115
         * account : dongdong.ding
         * authPermission : 0
         * realname : 丁冬冬
         */

        private String password;
        private int id;
        private String account;
        private int authPermission;
        private String realname;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getAuthPermission() {
            return authPermission;
        }

        public void setAuthPermission(int authPermission) {
            this.authPermission = authPermission;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
