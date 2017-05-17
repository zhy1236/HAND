package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.GsonUtil;
import com.example.hand.mockingbot.utils.HandApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 2017/5/5.
 */

public class ReceivedJournalAtivity extends BasicActivity implements AdapterView.OnItemClickListener {

    private List<ReceivedJournalEntity.ResultBean.DataBean> list = new ArrayList<>();
    private Toolbar toolbar;
    private ListView lv;
    private ReceivedJournalEntity receivedJournalEntity;
    private ListAdapter<ReceivedJournalEntity.ResultBean.DataBean> listAdapter;

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
        listAdapter = new ListAdapter<ReceivedJournalEntity.ResultBean.DataBean>(list, R.layout.journal_item) {
            @Override
            public void bindView(ViewHolder holder, ReceivedJournalEntity.ResultBean.DataBean obj) {
                String finishwork = "今日完成工作：<font color='#333333'>" + obj.getFinishWork() + "</font>";
                String unfinishwork = "今日完成工作：<font color='#333333'>" + obj.getUnfinishWork() + "</font>";
                String coordinationWork = "今日完成工作：<font color='#333333'>" + obj.getCoordinationWork() + "</font>";
                String realname = obj.getRealname();
                String time = getTime(obj.getSubmitDate());
                holder.setText(R.id.journal_item_tv_finish, Html.fromHtml(finishwork));
                holder.setText(R.id.journal_item_tv_unfinish, Html.fromHtml(unfinishwork));
                holder.setText(R.id.journal_item_tv_nedhellp, Html.fromHtml(coordinationWork));
                holder.setText(R.id.journal_item_tv_name,realname);
                holder.setText(R.id.journal_item_tv_time,time);
            }
        };
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);

    }

    private void initData() {
        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData() + "&state=0&pageNo=1&pageSize=100";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                receivedJournalEntity = GsonUtil.parseJsonToBean(string, ReceivedJournalEntity.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.addAll(receivedJournalEntity.getResult().getData());
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
        intent.putExtra("dailyId",list.get(i).getDailyId());
        intent.putExtra("time",getTime(list.get(i).getSubmitDate()));
        intent.putExtra("name",list.get(i).getRealname());
        startActivity(intent);
    }
}
