package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ResourceDailEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/12.
 */

public class ResourceDailyActivity extends BasicActivity {

    private String time;
    private int day;
    private Toolbar toolbar;
    private SimpleListView lv;
    private List<ResourceDailEntity.DataBeanX.DatedayBean> list = new ArrayList<>();
    private ListAdapter<ResourceDailEntity.DataBeanX.DatedayBean> listAdapter = new ListAdapter<ResourceDailEntity.DataBeanX.DatedayBean>(list, R.layout.item_job_datil) {
        @Override
        public void bindView(ViewHolder holder, ResourceDailEntity.DataBeanX.DatedayBean obj,int position) {
//            String submitDate = time + day;
//            String[] split = submitDate.split("-");
//            holder.setText(R.id.item_comment_time,split[0] + "年" + split[1] + "月" + split[2] + "日");
//            holder.setText(R.id.item_comment_tv,realname);
//            holder.setText(R.id.item_comment_tv_title,obj.getProductname());

        }
    };
    private RelativeLayout pb;
    private String realname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_journal);
        Bundle extras = getIntent().getExtras();
        time = extras.getString("time");
        day = extras.getInt("day");
        initView();
        loadData();
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
        title.setText("工作详情");
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
    }

    private void loadData() {
        final Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("product","");
        getmap.put("dateTime",time);
        getmap.put("pageNo",1);
        getmap.put("pageSize",10);
        getmap.put("resourceId",getIntent().getExtras().getInt("resourceId"));
        getmap.put("resourceTime",time +"-" + (day<9?("0" + day):("" + day)));
        HttpManager.getInstance().post(CommonValues.RESOURCE_LIST, getmap, ResourceDailEntity.class, new HttpManager.ResultCallback<ResourceDailEntity>() {
            @Override
            public void onSuccess(String json, final ResourceDailEntity entity) throws InterruptedException {
                if (entity.getCode() == 100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            realname = entity.getData().get(0).getRealname();
                            list = entity.getData().get(0).getDateday();
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }

            @Override
            public void onFailure(String msg) {
                finish();
            }
        });
    }
}
