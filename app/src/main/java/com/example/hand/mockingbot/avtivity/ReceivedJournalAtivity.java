package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.JournalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class ReceivedJournalAtivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<JournalBean> list = new ArrayList<>();
    private Toolbar toolbar;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_journal);
        initToolbar();
        initView();
        initData();
    }

    private void initToolbar() {
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
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.journal_receiver_lv);
        for (int i = 0; i < 10; i++) {
            JournalBean journalBean = new JournalBean();
            list.add(journalBean);
        }
        ListAdapter<JournalBean> listAdapter = new ListAdapter<JournalBean>(list,R.layout.journal_item) {
            @Override
            public void bindView(ViewHolder holder, JournalBean obj) {
                String str = "今日完成工作：<font color='#333333'>今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作</font>";
                holder.setText(R.id.journal_item_tv_finish, Html.fromHtml(str));
            }
        };
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);

    }

    private void initData() {
        //网络请求拿到数据
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
        startActivity(intent);
    }
}
