package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AddDailySeeEntivy;
import com.example.hand.mockingbot.entity.MyJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class SendJournalActivity extends BasicActivity implements AdapterView.OnItemClickListener, SimpleListView.OnRefreshListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<MyJournalEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<MyJournalEntity.ResultBean.DataBean> listAdapter = new ListAdapter<MyJournalEntity.ResultBean.DataBean>(list, R.layout.journal_item) {
        @Override
        public void bindView(ViewHolder holder, MyJournalEntity.ResultBean.DataBean obj,int position) {
            if (obj.getFinishWork() != null){
                holder.setText(R.id.journal_item_tv_finish, obj.getFinishWork());
            }else {
                holder.setText(R.id.journal_item_tv_finish, "");
            }
            if (obj.getUnfinishWork() != null){
                holder.setText(R.id.journal_item_tv_unfinish, obj.getUnfinishWork());
            }else {
                holder.setText(R.id.journal_item_tv_unfinish, "");
            }
            if (obj.getCoordinationWork() != null){
                holder.setText(R.id.journal_item_tv_nedhellp, obj.getCoordinationWork());
            }else {
                holder.setText(R.id.journal_item_tv_nedhellp, "");
            }
            if (obj.getRemark() != null){
                if (!obj.getRemark().replace(" ","").isEmpty()){
                    holder.setText(R.id.journal_item_tv_remark, obj.getRemark());
                    holder.setVisibility(R.id.journal_item_tv_remark,View.VISIBLE);
                }else {
                    holder.setVisibility(R.id.journal_item_tv_remark,View.GONE);
                }
            }
            String realname = obj.getRealname();
            String time = getTime(obj.getSubmitDate());
            holder.setText(R.id.journal_item_tv_name,realname);
            holder.setText(R.id.journal_item_tv_time,time);
            if (obj.getCountSee().equals("0")){
                holder.setVisibility(R.id.journal_item_tv_yd,View.GONE);
            }else {
                holder.setVisibility(R.id.journal_item_tv_yd,View.VISIBLE);
            }
        }
    };
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_journal);
        initToobar();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        TextView title = (TextView) findViewById(R.id.main_tv_title);
        if (!getIntent().getExtras().getString("userId").equals(HandApp.getLoginEntity().getResult().getData().getId())){
            title.setText("所有日报");
        }
    }

    private void loadData(final int index) {
        userId = getIntent().getExtras().getString("userId");
        String url = CommonValues.MY_JOURNAL + "userId=" + userId + "&pageNo=" + index + "&pageSize=10";
        HttpManager.getInstance().get(url, MyJournalEntity.class, new HttpManager.ResultCallback<MyJournalEntity>() {
            @Override
            public void onSuccess(String json, final MyJournalEntity myJournalEntity) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (index == 1){
                            list.clear();
                        }
                        hasMore = (myJournalEntity.getResult().getData().size() > 0);
                        list.addAll(myJournalEntity.getResult().getData());
                        listAdapter.notifyDataSetChanged();
                        lv.completeRefresh();
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
            adddailySee(list.get(i-1).getDailyId());
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
            intent.putExtra("dailyId",list.get(i-1).getDailyId());
            intent.putExtra("time",getTime(list.get(i-1).getSubmitDate()));
            intent.putExtra("name",list.get(i-1).getRealname());
            if (userId.equals(HandApp.getLoginEntity().getResult().getData().getId())){
                intent.putExtra("my",true);
            }else {
                intent.putExtra("my",false);
            }
            startActivity(intent);
        }

    }

    private void adddailySee(int id) {
        String url = CommonValues.ADD_DAILYSEE + "userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyId=" + id;
        HttpManager.getInstance().get(url, AddDailySeeEntivy.class, new HttpManager.ResultCallback<AddDailySeeEntivy>() {
            @Override
            public void onSuccess(String json, AddDailySeeEntivy addDailySeeEntivy) throws InterruptedException {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
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
