package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.entity.ResultEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/16.
 */

public class MyCollectionActivity extends BasicActivity implements AdapterView.OnItemClickListener, SimpleListView.OnRefreshListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private ReceivedJournalEntity receivedJournalEntity;
    private List<ReceivedJournalEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<ReceivedJournalEntity.ResultBean.DataBean> listAdapter = new ListAdapter<ReceivedJournalEntity.ResultBean.DataBean>(list, R.layout.journal_item) {
        @Override
        public void bindView(ViewHolder holder, final ReceivedJournalEntity.ResultBean.DataBean obj,int position) {
            if (obj.getRealname().equals(HandApp.getLoginEntity().getResult().getData().getRealname())){
                if (HandApp.getPhotoUri() != null){
                    ImageView iv = holder.getView(R.id.journal_item_imv);
                    iv.setImageURI(HandApp.getPhotoUri());
                }
            }else {
                holder.setImageResource(R.id.journal_item_imv,R.mipmap.ic_head_portrait);
            }
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
                if (!obj.getRemark().isEmpty()){
                    holder.setText(R.id.journal_item_tv_remark, obj.getRemark());
                    holder.setVisibility(R.id.journal_item_tv_remark,View.VISIBLE);
                    holder.setVisibility(R.id.journal_item_remark,View.VISIBLE);
                }else {
                    holder.setVisibility(R.id.journal_item_tv_remark,View.GONE);
                    holder.setVisibility(R.id.journal_item_remark,View.GONE);
                }
            }
            String realname = obj.getRealname();
            String time = getTime(obj.getSubmitDate());
            holder.setText(R.id.journal_item_tv_name,realname);
            holder.setText(R.id.journal_item_tv_time,time);
            holder.setVisibility(R.id.journal_iv_delete,View.VISIBLE);
            holder.setOnClickListener(R.id.journal_iv_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletefocus(obj);
                }
            });
        }
    };

    private void deletefocus(final ReceivedJournalEntity.ResultBean.DataBean obj) {
        String url = CommonValues.FOCUS + "userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyId=" + obj.getDailyId() + "&state=0";
        HttpManager.getInstance().get(url, ResultEntity.class, new HttpManager.ResultCallback<ResultEntity>() {
            @Override
            public void onSuccess(String json, ResultEntity entity) throws InterruptedException {
                if (entity.getResult().getData().equals("1")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(obj);
                            Toast.makeText(getApplicationContext(),"删除收藏成功",Toast.LENGTH_SHORT).show();
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_journal);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(index);
    }

    private void loadData(final int index) {
        String url = CommonValues.JOURNAL_LIST + "userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyTime=" + getData(true) + "&state=0&pageNo=" + index +"&pageSize=10&focus=1";
        HttpManager.getInstance().get(url, ReceivedJournalEntity.class, new HttpManager.ResultCallback<ReceivedJournalEntity>() {
            @Override
            public void onSuccess(String json, ReceivedJournalEntity receivedJournal) throws InterruptedException {
                receivedJournalEntity = receivedJournal;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (index == 1){
                            list.clear();
                        }
                        list.addAll(receivedJournalEntity.getResult().getData());
                        hasMore = (receivedJournalEntity.getResult().getData().size() > 0);
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
        lv = (SimpleListView) findViewById(R.id.collection_journal_lv);
        lv.setAdapter(listAdapter);
        lv.setOnRefreshListener(this);
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
            intent.putExtra("focus",list.get(i-1).getFocus());
            intent.putExtra("my",false);
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
