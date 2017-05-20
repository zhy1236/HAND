package com.example.hand.mockingbot.entity;

import java.util.List;

/**
 * Created by zhy on 2017/5/17.
 */

public class ReceivedJournalEntity {
    /**
     * result : {"data":[{"finishWork":"项目管理平台\n1.app接口调试","unfinishWork":"","isReadFlag":"0","submitDate":1494927000000,"coordinationWork":"","focus":"0","remark":"","dailyId":252,"userId":107,"value":"后端开发","commentCount":"1","realname":"韩西德","enclosureUrl":"1493368992992__1509888__201702-04-项目综合管理平台需求规格说明书-V2.1.doc,1493804529345__3293274__伊利智能BI app-最新.zip"},{"finishWork":"1.DSMP\n 1.1ELT打包到集群测试\n 1.2 解决任务的zip包不能上传到azkaban问题","unfinishWork":"","isReadFlag":"0","submitDate":1494515779000,"coordinationWork":"","focus":"0","remark":"","dailyId":248,"userId":114,"value":"大数据","commentCount":"2","realname":"向聪"},{"finishWork":"1、合同管理系统日常测试\n2、学习搭建接口测试自动化框架（eclipse+Maven+testNG+reportNG）","unfinishWork":"","submitDate":1494510312000,"focus":"0","remark":"","userId":82,"commentCount":"2","realname":"程爽爽","enclosureUrl":"1493368992992__1509888__201702-04-项目综合管理平台需求规格说明书-V2.1.doc,1493804529345__3293274__伊利智能BI app-最新.zip","isReadFlag":"0","coordinationWork":"","dailyId":247,"value":"测试"},{"finishWork":"潍柴项目反思；京博石化PPT撰写","unfinishWork":"京博石化场景收集","isReadFlag":"0","submitDate":1494509488000,"coordinationWork":"","focus":"0","remark":"","dailyId":246,"userId":99,"value":"项目经理","commentCount":"0","realname":"赵华新"},{"finishWork":"1 dmp\n1.1 集成piwik js，支持piwik方式发送埋点数据，后端接口兼容。\n1.2 重新梳理服务器前端项目发布脚本\n1.3 项目进度跟进，整理菜单\n2 电话面试","unfinishWork":"","isReadFlag":"0","submitDate":1494509460000,"coordinationWork":"","focus":"0","remark":"","dailyId":245,"userId":8,"value":"开发主管","commentCount":"0","realname":"赵国成"},{"finishWork":"埋点系统：\n  1、埋点Token管理--页面编制以及页面中功能联调\n  2、在埋点模拟设置中添加Token码选择\n  3、埋点监控(10%)","unfinishWork":"","isReadFlag":"0","submitDate":1494506485000,"coordinationWork":"","focus":"0","remark":"","dailyId":244,"userId":19,"value":"前端开发","commentCount":"0","realname":"向芳"},{"finishWork":"1、与技术中心确认参加当天（5-11）现场面试人员名单及一面评价。\n2、确认5-12现场面试人员名单，发放面试通知。\n3、了解现场面试主考官对参加5-10现场面试人选质量问题的意见，向简历推荐方提出把关简历真实性的要求。\n4、与现场面试合格人员联系，整理面试结果及个人信息资料，提出录用建议。\n5、了解新近入职同事提出离职原因。","unfinishWork":"","isReadFlag":"0","submitDate":1494506317000,"coordinationWork":"","focus":"0","remark":"","dailyId":243,"userId":105,"value":"其他","commentCount":"0","realname":"李蕾"},{"finishWork":"1 UEBA\n1.1 跟杨超统一讲需要修改的页面；\n1.2 完善需求功能表；\n1.3 与张辉沟通操作告警上的业务逻辑；","unfinishWork":"","isReadFlag":"0","submitDate":1494498526000,"coordinationWork":"","focus":"0","remark":"","dailyId":242,"userId":25,"value":"产品经理","commentCount":"0","realname":"袁娟娟"},{"finishWork":"1,沟通发票盖章事宜\n2， 部门 报销各项事宜\n3， 白板到货，和丁双，王成龙一起安装\n4，加班零食采购","unfinishWork":"","isReadFlag":"0","submitDate":1494497307000,"coordinationWork":"","focus":"0","remark":"","dailyId":241,"userId":30,"value":"其他","commentCount":"0","realname":"黄冲纳"},{"finishWork":"项目综合管理平台移动端：\n1.我的关注，关注日志，关注用户，关注项目三页；\n2.补充图标，切图，交付安卓开发。","unfinishWork":"","isReadFlag":"0","submitDate":1494496009000,"coordinationWork":"","focus":"0","remark":"","dailyId":240,"userId":47,"value":"UI设计","commentCount":"0","realname":"屈冰霄"}],"page":{"page_no":1,"page_size":10,"total_pages":19,"total_elements":188,"number_elements":10}}
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
        private PageBean page;
        private List<DataBean> data;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PageBean {
            /**
             * page_no : 1
             * page_size : 10
             * total_pages : 19
             * total_elements : 188
             * number_elements : 10
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

        public static class DataBean {

            private String finishWork;
            private String unfinishWork;
            private String isReadFlag;
            private long submitDate;
            private String coordinationWork;
            private String focus;
            private String remark;
            private int dailyId;
            private int userId;
            private String value;
            private String commentCount;
            private String realname;
            private String enclosureUrl;

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

            public String getIsReadFlag() {
                return isReadFlag;
            }

            public void setIsReadFlag(String isReadFlag) {
                this.isReadFlag = isReadFlag;
            }

            public long getSubmitDate() {
                return submitDate;
            }

            public void setSubmitDate(long submitDate) {
                this.submitDate = submitDate;
            }

            public String getCoordinationWork() {
                return coordinationWork;
            }

            public void setCoordinationWork(String coordinationWork) {
                this.coordinationWork = coordinationWork;
            }

            public String getFocus() {
                return focus;
            }

            public void setFocus(String focus) {
                this.focus = focus;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getEnclosureUrl() {
                return enclosureUrl;
            }

            public void setEnclosureUrl(String enclosureUrl) {
                this.enclosureUrl = enclosureUrl;
            }
        }
    }
}
