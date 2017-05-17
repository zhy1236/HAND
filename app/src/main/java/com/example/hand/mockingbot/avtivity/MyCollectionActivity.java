package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.JournalBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/16.
 */

public class MyCollectionActivity extends BasicActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<JournalBean> list = new ArrayList<>();
    private ListAdapter<JournalBean> listAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_journal);
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
        for (int i = 0; i < 10; i++) {
            JournalBean journalBean = new JournalBean();
            list.add(journalBean);
        }
        listView = (ListView) findViewById(R.id.collection_journal_lv);
        listAdapter = new ListAdapter<JournalBean>(list, R.layout.journal_item) {
            @Override
            public void bindView(final ViewHolder holder, final JournalBean obj) {
                holder.setVisibility(R.id.journal_iv_delete,View.VISIBLE);
                String str = "今日完成工作：<font color='#333333'>" + "今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作" + "</font>";
                holder.setText(R.id.journal_item_tv_finish, Html.fromHtml(str));
                holder.setOnClickListener(R.id.journal_iv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listAdapter.remove(obj);
                                Toast.makeText(getApplicationContext(),holder.getItemPosition() + "",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        };
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
        startActivity(intent);
    }
}
