package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/4/25.
 */

public class LoginEntity {

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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {

            private String id;
            private String account;
            private String password;
            private String realname;
            private String authPermission;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAuthPermission() {
                return authPermission;
            }

            public void setAuthPermission(String authPermission) {
                this.authPermission = authPermission;
            }
        }
    }

    public static class ErrorBean {

        private String message;
        private String type;
        private int code;
        private String trace_id;
        private int error_subcode;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getTrace_id() {
            return trace_id;
        }

        public void setTrace_id(String trace_id) {
            this.trace_id = trace_id;
        }

        public int getError_subcode() {
            return error_subcode;
        }

        public void setError_subcode(int error_subcode) {
            this.error_subcode = error_subcode;
        }
    }
}
