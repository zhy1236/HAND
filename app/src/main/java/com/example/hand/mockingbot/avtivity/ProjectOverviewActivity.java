package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ProjectOverviewEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/15.
 */

public class ProjectOverviewActivity extends BasicActivity implements SimpleListView.OnRefreshListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<ProjectOverviewEntity.DataBean> list = new ArrayList<>();
    private ListAdapter<ProjectOverviewEntity.DataBean> listAdapter = new ListAdapter<ProjectOverviewEntity.DataBean>(list, R.layout.item_project_overview) {
        @Override
        public void bindView(ViewHolder holder, final ProjectOverviewEntity.DataBean obj, int position) {
            holder.setText(R.id.item_project_overview_name,obj.getName());
            holder.setText(R.id.item_project_overview_pg,obj.getProjectState());
            holder.setOnClickListener(R.id.item_project_overview_btn, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toOverview(obj);
                }
            });
        }
    };

    private void toOverview(ProjectOverviewEntity.DataBean obj) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ViewProjectOverviewActivity.class);
        intent.putExtra("id",obj.getId() + "");
        intent.putExtra("name",obj.getName());
        intent.putExtra("projectImprotance",obj.getProjectImprotance());
        intent.putExtra("projectRisk",obj.getProjectRisk());
        intent.putExtra("projectManager",obj.getProjectManager());
        intent.putExtra("cpjlName",obj.getCpjlName());
        intent.putExtra("beginTime",obj.getBeginTime());
        intent.putExtra("endtime",obj.getEndTime());
        intent.putExtra("projectStage",obj.getProjectStage());
        intent.putExtra("projectState",obj.getProjectState());
        intent.putExtra("overallProgress",obj.getOverallProgress());
        startActivity(intent);
    }

    private RelativeLayout pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_journal);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(index);
    }

    private void loadData(int indx) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("pageNo",indx);
        getmap.put("pageSize",10);
        HttpManager.getInstance().post(CommonValues.CUS_PROJECT, getmap, ProjectOverviewEntity.class, new HttpManager.ResultCallback<ProjectOverviewEntity>() {
            @Override
            public void onSuccess(String json, final ProjectOverviewEntity entity) throws InterruptedException {
                if (entity.getCode().equals("100")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            if (index == 1){
                                list.clear();
                                if (entity.getData().size() == 0){
                                    hasMore = false;
                                }
                                list.addAll(entity.getData());
                                listAdapter.notifyDataSetChanged();
                                lv.completeRefresh();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
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
        TextView title = (TextView) findViewById(R.id.main_tv_title);
        title.setText("项目概览");
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
    }

    @Override
    public void onPullRefresh() {
        hasMore = true;
        index = 1;
        pb.setVisibility(View.VISIBLE);
        loadData(index);
    }

    @Override
    public void onLoadingMore() {
        if (hasMore) {
            index += 1;
            pb.setVisibility(View.VISIBLE);
            loadData(index);
        } else {
            lv.completeRefresh();
        }
    }

    @Override
    public void onScrollOutside() {

    }
}
