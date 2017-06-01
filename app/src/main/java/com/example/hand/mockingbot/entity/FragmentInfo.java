package com.example.hand.mockingbot.entity;

import android.support.v4.app.Fragment;

/**
 * Created by sy_heima on 2016/7/22.
 */
public class FragmentInfo {
    //标题
    public String title;
    //fragment
    public Fragment fragment;

    public FragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }
}
