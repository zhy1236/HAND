package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ResourceOccupationEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/7.
 */

public class ResourceOccupationActivity extends BasicActivity implements SimpleListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<ResourceOccupationEntity.DataBean> list = new ArrayList<>();
    private ListAdapter<ResourceOccupationEntity.DataBean> listAdapter = new ListAdapter<ResourceOccupationEntity.DataBean>(list, R.layout.item_resource_occupation) {
        @Override
        public void bindView(ViewHolder holder, ResourceOccupationEntity.DataBean obj,int position) {
            holder.setText(R.id.item_resource_occupation_name,obj.getRealname());
            holder.setText(R.id.item_resource_occupation_day,obj.getDays() + "天");
            ProgressBar pb = holder.getView(R.id.item_resource_occupation_pb);
            pb.setMax(i);
            pb.setProgress(obj.getDays());
            holder.setText(R.id.item_resource_occupation_sum,obj.getTotal() + "");
            holder.setText(R.id.item_resource_occupation_taskscompleted,obj.getTasksCompleted() + "");

        }
    };
    private RelativeLayout pb;
    private String time;
    private String days;
    private String[] split;
    private int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_occupation);
        String data = getData(true);
        split = data.split("-");
        time = split[0] + "/" + split[1];
        if (split[2].startsWith("0")){
            days = split[2].substring(1,2);
        }else {
            days = split[2];
        }
        Integer integer = new Integer(days);
        i = integer.intValue();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(index);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
        TextView tv_time = (TextView) findViewById(R.id.time);
        tv_time.setText(time);
        TextView tv_days = (TextView) findViewById(R.id.item_resource_occupation_days);
        tv_days.setText(days);
    }

    @Override
    public void onPullRefresh() {
        hasMore = true;
        index = 1;
        loadData(index);
        pb.setVisibility(View.VISIBLE);
    }

    private void loadData(int indx) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("product","");
        getmap.put("dateTime",time.replace("/","-"));
        getmap.put("pageNo",indx);
        getmap.put("pageSize",10);
        HttpManager.getInstance().post(CommonValues.RESOURCE_LIST, getmap, ResourceOccupationEntity.class, new HttpManager.ResultCallback<ResourceOccupationEntity>() {
            @Override
            public void onSuccess(String json, final ResourceOccupationEntity entity) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        if (index == 1){
                            list.clear();
                        }
                        list.addAll(entity.getData());
                        hasMore = (entity.getData().size() > 0);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        toResource(list.get(i - 1));
    }

    private void toResource(ResourceOccupationEntity.DataBean dataBean) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ResourcePersonActivity.class);
        intent.putExtra("product",dataBean.getId());
        intent.putExtra("resourceId",dataBean.getId());
        startActivity(intent);
    }
}
