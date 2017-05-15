package com.example.hand.mockingbot.utils;

/**
 * Created by Oliver on 2016/9/13.
 */

public class CommonValues {
//    public static final String BASE_TEST_HOST = "http://10.211.55.174:9090/proiect-mg-app/app/daily"; //本地环境
    public static final String BASE_TEST_HOST = "http://192.168.11.198:8087/proiect-mg-app/app";//测试环境

    public static String currentHost = BASE_TEST_HOST;

    /**
     * 登陆
     */
    public static final String LOGIN = currentHost + "/login";

    /**
     * 日志列表
     */
    public static final String JOURNAL_LIST = currentHost + "/selectAllDaily";

    /**
     * 日志详情
     */
    public static final String JOURNAL_DETAIL = currentHost + "/dailyDetail";

    /**
     * 获得评论
     */
    public static final String GET_COMMENT = currentHost + "/comment";

    /**
     * 评论
     */
    public static final String COMMENT = currentHost + "/addComment";

    /**
     * 发日志
     */
    public static final String NEW_JOURNAL = currentHost + "/addDaily";

    /**
     * 上传附件
     */
    public static final String UP_LOAD_ATTAUCHMENT = currentHost + "/uploadAttachment";

}
