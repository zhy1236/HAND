package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.DefectEntity;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class DefectJournalActivity extends BasicActivity implements  SimpleListView.OnRefreshListener {


    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<DefectEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<DefectEntity.ResultBean.DataBean> listAdapter = new ListAdapter<DefectEntity.ResultBean.DataBean>(list, R.layout.item_defect) {
        @Override
        public void bindView(ViewHolder holder, DefectEntity.ResultBean.DataBean obj,int position) {
            if (obj == list.get(0)){
                holder.setVisibility(R.id.item_defect_top,View.VISIBLE);
            }else {
                holder.setVisibility(R.id.item_defect_top,View.GONE);
            }
            holder.setText(R.id.item_defect_tv,obj.getRealname());
            holder.setText(R.id.item_defect_zw,obj.getPosition());
        }
    };
    private RelativeLayout pb;
    private Button button_checked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_journal);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(index);
    }

    private void loadData(final int ind) {
        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData() + "&state=1&pageNo=" + ind + "&pageSize=10";
        HttpManager.getInstance().get(url, DefectEntity.class, new HttpManager.ResultCallback<DefectEntity>() {
            @Override
            public void onSuccess(String json, final DefectEntity receivedJournal) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        if (index == 1){
                            list.clear();
                        }
                        list.addAll(receivedJournal.getResult().getData());
                        hasMore = (receivedJournal.getResult().getData().size() > 0);
                        listAdapter.notifyDataSetChanged();
                        lv.completeRefresh();
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
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
        TextView viewById = (TextView) findViewById(R.id.main_tv_title);
        viewById.setText("未提交人员");
        button_checked = (Button) findViewById(R.id.btn_checked_project);
        button_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelector(button_checked,new String[]{"项目综合管理平台","伊利只能BI平台","吉利BPM项目","东方购物"});
            }
        });
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setDividerHeight(0);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
    }

    @Override
    public void onPullRefresh() {
        hasMore = true;
        index = 1;
        loadData(index);
        pb.setVisibility(View.VISIBLE);
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
