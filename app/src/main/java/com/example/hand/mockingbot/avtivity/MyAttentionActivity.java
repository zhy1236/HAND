package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.MainFragmentAdapter;
import com.example.hand.mockingbot.entity.FragmentInfo;
import com.example.hand.mockingbot.fagment.AttentionPersonFragment;
import com.example.hand.mockingbot.view.PagerSlidingTab;

import java.util.ArrayList;

/**
 * Created by zhy on 2017/6/1.
 */

public class MyAttentionActivity extends BasicActivity {

    private ArrayList<FragmentInfo> mShowItems = new ArrayList<>();
    private PagerSlidingTab mPstMainTitle;
    private ViewPager mVpMainShows;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
//        设置左侧图标和点击事件
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mPstMainTitle = (PagerSlidingTab) findViewById(R.id.pst_main_title);
        mVpMainShows = (ViewPager) findViewById(R.id.vp_main_shows);
        MainFragmentAdapter fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        String[] titles = new String[]{"关注项目","关注用户"};
        mShowItems.add(new FragmentInfo(titles[1], new AttentionPersonFragment()));
//        mShowItems.add(new FragmentInfo(titles[0], new AttentionProjectFragment()));
//        mShowItems.add(new FragmentInfo(titles[1], new AttentionPersonFragment()));
        fragmentAdapter.setShowItems(mShowItems);
        mVpMainShows.setAdapter(fragmentAdapter);
        mPstMainTitle.setViewPager(mVpMainShows);
    }
}
