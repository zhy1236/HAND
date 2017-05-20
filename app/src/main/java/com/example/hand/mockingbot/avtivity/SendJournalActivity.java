package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.entity.MyJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.GsonUtil;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.view.SimpleListView;

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

public class SendJournalActivity extends BasicActivity implements AdapterView.OnItemClickListener, SimpleListView.OnRefreshListener {

    private int content = 0;
    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private MyJournalEntity receivedJournalEntity;
    private List<MyJournalEntity.ResultBean.DataBean> list = new ArrayList<>();
    private List<MyJournalEntity.ResultBean.DataBean> basicList;
    private ListAdapter<MyJournalEntity.ResultBean.DataBean> listAdapter = new ListAdapter<MyJournalEntity.ResultBean.DataBean>(list, R.layout.journal_item) {
        @Override
        public void bindView(ViewHolder holder, MyJournalEntity.ResultBean.DataBean obj) {
            String finishwork = "今日完成工作：<font color='#333333'>" + obj.getFinishWork() + "</font>";
            String unfinishwork = "今日完成工作：<font color='#333333'>" + obj.getUnfinishWork() + "</font>";
            String coordinationWork = "今日完成工作：<font color='#333333'>" + obj.getCoordinationWork() + "</font>";
            String remark = "今日完成工作：<font color='#333333'>" + obj.getRemark() + "</font>";
            String realname = obj.getRealname();
            String time = getTime(obj.getSubmitDate());
            holder.setText(R.id.journal_item_tv_finish, Html.fromHtml(finishwork));
            holder.setText(R.id.journal_item_tv_unfinish, Html.fromHtml(unfinishwork));
            holder.setText(R.id.journal_item_tv_nedhellp, Html.fromHtml(coordinationWork));
            holder.setText(R.id.journal_item_tv_name,realname);
            holder.setText(R.id.journal_item_tv_time,time);
            if (!remark.isEmpty()){
                holder.setText(R.id.journal_item_tv_remark, Html.fromHtml(remark));
                holder.setVisibility(R.id.journal_item_tv_remark,View.VISIBLE);
            }else {
                holder.setVisibility(R.id.journal_item_tv_remark,View.GONE);
            }
            if (obj.getCountSee().equals("0")){
                holder.setVisibility(R.id.journal_item_tv_yd,View.VISIBLE);
            }else {
                holder.setVisibility(R.id.journal_item_tv_yd,View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_journal);
        Intent intent = getIntent();
        content = intent.getExtras().getInt("content");
        initToobar();
        initView();
        loadData(index);
    }

    private void initToobar() {
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

    private void loadData(final int index) {
        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.MY_JOURNAL + "userId=" + data.getId() + "&pageNo=" + index + "&pageSize=10";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv.completeRefresh();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                receivedJournalEntity = GsonUtil.parseJsonToBean(string, MyJournalEntity.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (index == 1){
                            list.clear();
                        }
                        list.addAll(receivedJournalEntity.getResult().getData());
                        if (list.size() == content){
                            hasMore = false;
                        }
                        listAdapter.notifyDataSetChanged();
                        hasMore = receivedJournalEntity.getResult().getData().size() > 0;
                        lv.completeRefresh();
                    }
                });
            }
        });
    }

    private void initView() {
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (lv.isPullRefreshed()){
            return;
        }else {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
            intent.putExtra("dailyId",list.get(i-1).getDailyId());
            intent.putExtra("time",getTime(list.get(i-1).getSubmitDate()));
            intent.putExtra("name",list.get(i-1).getRealname());
            intent.putExtra("my",true);
            startActivity(intent);
        }

    }

    @Override
    public void onPullRefresh() {
        hasMore = true;
        index = 1;
        loadData(index);
    }

    @Override
    public void onLoadingMore() {
        if (hasMore) {
            index += 1;
            loadData(index);
        } else {
            lv.completeRefresh();
        }
    }

    @Override
    public void onScrollOutside() {

    }

}
