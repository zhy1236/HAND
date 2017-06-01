package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.MainFragmentAdapter;
import com.example.hand.mockingbot.entity.FragmentInfo;
import com.example.hand.mockingbot.fagment.AttentionPersonFragment;
import com.example.hand.mockingbot.fagment.AttentionProjectFragment;
import com.example.hand.mockingbot.view.PagerSlidingTab;

import java.util.ArrayList;

/**
 * Created by zhy on 2017/6/1.
 */

public class MyAttentionActivity extends BasicActivity {

    private ArrayList<FragmentInfo> mShowItems = new ArrayList<>();
    private PagerSlidingTab mPstMainTitle;
    private ViewPager mVpMainShows;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
    }

    private void initView() {
        mPstMainTitle = (PagerSlidingTab) findViewById(R.id.pst_main_title);
        mVpMainShows = (ViewPager) findViewById(R.id.vp_main_shows);
        MainFragmentAdapter fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        String[] titles = new String[]{"关注项目","关注用户"};
        mShowItems.add(new FragmentInfo(titles[0], new AttentionProjectFragment()));
        mShowItems.add(new FragmentInfo(titles[1], new AttentionPersonFragment()));
        fragmentAdapter.setShowItems(mShowItems);

        mVpMainShows.setAdapter(fragmentAdapter);

        // 指示器跟viewpager关联
        mPstMainTitle.setViewPager(mVpMainShows);
    }
}
