package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/6/6.
 */

public class UsersShowEntity {

    private String msg;
    private UsersEntity.DataBeanX.DataBean bean;

    public UsersEntity.DataBeanX.DataBean getBean() {
        return bean;
    }

    public void setBean(UsersEntity.DataBeanX.DataBean bean) {
        this.bean = bean;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
