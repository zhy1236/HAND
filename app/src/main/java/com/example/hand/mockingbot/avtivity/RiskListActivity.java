package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/6/15.
 */

public class RiskListActivity extends BasicActivity implements SimpleListView.OnRefreshListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<ReceivedJournalEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<ReceivedJournalEntity.ResultBean.DataBean> listAdapter = new ListAdapter<ReceivedJournalEntity.ResultBean.DataBean>(list, R.layout.item_add_risk) {
        @Override
        public void bindView(ViewHolder holder, final ReceivedJournalEntity.ResultBean.DataBean obj, int position) {
            holder.setOnClickListener(R.id.item_add_risk_btn_change, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toModifyRisk(obj);
                }
            });
            holder.setOnClickListener(R.id.item_add_risk_btn_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteRisk(obj);
                }
            });
        }
    };

    private void deleteRisk(final ReceivedJournalEntity.ResultBean.DataBean obj) {
        //// TODO: 2017/6/15 网络请求删除风险
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.remove(obj);
                listAdapter.notifyDataSetChanged();
                lv.completeRefresh();
            }
        });
    }

    private void toModifyRisk(ReceivedJournalEntity.ResultBean.DataBean obj) {
        //// TODO: 2017/6/15 跳转到修改风险界面
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ModifyRiskActivity.class);
        //传递参数
        startActivity(intent);
    }

    private RelativeLayout pb;
    private Button btn_add;

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
        btn_add = (Button) findViewById(R.id.journal_receiver_btn_search);
        btn_add.setVisibility(View.VISIBLE);
        btn_add.setBackgroundResource(R.mipmap.ic_add_src);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRisk();
            }
        });
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);

    }

    private void loadData(int indx) {
        //// TODO: 2017/6/15 网络请求获取数据
    }

    private void addRisk() {
        //// TODO: 2017/6/15 跳转到增加问题界面
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
