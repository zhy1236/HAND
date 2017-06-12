package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/6/12.
 */

public class ResourceDailEntity {
    /**
     * msg : 操作成功
     * code : 100
     * data : [{"del_flag":0,"project":10,"days":12,"dateday":[{"data":[{"taskname":"项目综合管理平台测试任务","productname":"爱力诺企业内容管理-流程管理-合同管理","setdays":[1,2,3,4,5,6,7,8,9,10,11,12]}],"productname":"爱力诺企业内容管理-流程管理-合同管理"}],"position":"测试","id":23,"account":"yq.zhong","realname":"钟依倩"}]
     * page : {"page_no":1,"page_size":10,"total_pages":1,"total_elements":1,"number_elements":1}
     */

    private String msg;
    private int code;
    private PageBean page;
    private List<DataBeanX> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * page_no : 1
         * page_size : 10
         * total_pages : 1
         * total_elements : 1
         * number_elements : 1
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

    public static class DataBeanX {
        /**
         * del_flag : 0
         * project : 10
         * days : 12
         * dateday : [{"data":[{"taskname":"项目综合管理平台测试任务","productname":"爱力诺企业内容管理-流程管理-合同管理","setdays":[1,2,3,4,5,6,7,8,9,10,11,12]}],"productname":"爱力诺企业内容管理-流程管理-合同管理"}]
         * position : 测试
         * id : 23
         * account : yq.zhong
         * realname : 钟依倩
         */

        private int del_flag;
        private int project;
        private int days;
        private String position;
        private int id;
        private String account;
        private String realname;
        private List<DatedayBean> dateday;

        public int getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(int del_flag) {
            this.del_flag = del_flag;
        }

        public int getProject() {
            return project;
        }

        public void setProject(int project) {
            this.project = project;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public List<DatedayBean> getDateday() {
            return dateday;
        }

        public void setDateday(List<DatedayBean> dateday) {
            this.dateday = dateday;
        }

        public static class DatedayBean {
            /**
             * data : [{"taskname":"项目综合管理平台测试任务","productname":"爱力诺企业内容管理-流程管理-合同管理","setdays":[1,2,3,4,5,6,7,8,9,10,11,12]}]
             * productname : 爱力诺企业内容管理-流程管理-合同管理
             */

            private String productname;
            private List<DataBean> data;

            public String getProductname() {
                return productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * taskname : 项目综合管理平台测试任务
                 * productname : 爱力诺企业内容管理-流程管理-合同管理
                 * setdays : [1,2,3,4,5,6,7,8,9,10,11,12]
                 */

                private String taskname;
                private String productname;
                private List<Integer> setdays;

                public String getTaskname() {
                    return taskname;
                }

                public void setTaskname(String taskname) {
                    this.taskname = taskname;
                }

                public String getProductname() {
                    return productname;
                }

                public void setProductname(String productname) {
                    this.productname = productname;
                }

                public List<Integer> getSetdays() {
                    return setdays;
                }

                public void setSetdays(List<Integer> setdays) {
                    this.setdays = setdays;
                }
            }
        }
    }
}
