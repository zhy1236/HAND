package com.example.hand.mockingbot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hand.mockingbot.entity.FragmentInfo;

import java.util.ArrayList;

/**
 * Created by sy_heima on 2016/7/22.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    // 定义一个fragment集合
    private ArrayList<FragmentInfo> mShowItems = new ArrayList<>();

    //传入集合
    public void setShowItems(ArrayList<FragmentInfo> fragments) {
        this.mShowItems = fragments;
    }

    //返回一个fragment
    @Override
    public Fragment getItem(int position) {
        return mShowItems.get(position).fragment;
    }

    //返回当前的viewpager展示多少个
    @Override
    public int getCount() {
        return mShowItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mShowItems.get(position).title;
    }
}
