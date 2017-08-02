package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.Entity;
import com.example.hand.mockingbot.entity.IssueTypeEntity;
import com.example.hand.mockingbot.entity.RiskListEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.ToastUtil;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/15.
 */

public class RiskListActivity extends BasicActivity implements SimpleListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<RiskListEntity.DataBean> list = new ArrayList<>();
    private ListAdapter<RiskListEntity.DataBean> listAdapter = new ListAdapter<RiskListEntity.DataBean>(list, R.layout.item_add_risk) {
        @Override
        public void bindView(ViewHolder holder, final RiskListEntity.DataBean obj, int position) {
            holder.setText(R.id.item_add_risk_type,obj.getIssueTypeName());
            holder.setText(R.id.item_add_risk_time,obj.getCreatDate());
            holder.setText(R.id.item_add_risk_content,obj.getIssueDesc());
            holder.setText(R.id.item_add_risk_name,obj.getCreater());
            holder.setOnClickListener(R.id.item_add_risk_btn_processing, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toProcessingRisk(obj);
                }
            });
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

    private String projectNo;
    private String[] issueType;
    private TextView tv_issueType;
    private List<IssueTypeEntity.DataBean> issueTypeList;
    private int issueTypeId;

    private void toProcessingRisk(RiskListEntity.DataBean obj) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),AddRiskActivity.class);
        intent.putExtra("type",2);
        intent.putExtra("issuetypename",obj.getIssueTypeName());
        intent.putExtra("creater",obj.getCreater());
        intent.putExtra("creatdate",obj.getCreatDate());
        intent.putExtra("issuedesc",obj.getIssueDesc());
        intent.putExtra("projectNo",projectNo);
        intent.putExtra("issueId",obj.getIssueId());
        intent.putExtra("modifiername",obj.getModifierName());
        intent.putExtra("modifierDate",obj.getModifierDate());
        startActivity(intent);
    }

    /**
     * 删除风险
     */
    private void deleteRisk(final RiskListEntity.DataBean obj) {
        pb.setVisibility(View.VISIBLE);
        final Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("issueId",obj.getIssueId());
        HttpManager.getInstance().post(CommonValues.DELETE_CUS_ISSUE, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
            @Override
            public void onSuccess(String json, final Entity entity) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity.getCode().equals("100")){
                            pb.setVisibility(View.GONE);
                            ToastUtil.showToast(getApplicationContext(),"风险删除成功");
                            list.remove(obj);
                            listAdapter.notifyDataSetChanged();
                            lv.completeRefresh();
                        }else {
                            ToastUtil.showToast(getApplicationContext(),entity.getMsg());
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        ToastUtil.showToast(getApplicationContext(),"风险删除失败，请检查网络后再次尝试！");
                    }
                });
            }
        });
    }

    /**
     * 跳转到修改风险界面
     */
    private void toModifyRisk(RiskListEntity.DataBean obj) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),AddRiskActivity.class);
        intent.putExtra("type",1);
        intent.putExtra("issuetypename",obj.getIssueTypeName());
        intent.putExtra("creater",obj.getCreater());
        intent.putExtra("creatdate",obj.getCreatDate());
        intent.putExtra("issuedesc",obj.getIssueDesc());
        intent.putExtra("issueId",obj.getIssueId());
        startActivity(intent);
    }

    private RelativeLayout pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_list);
        Bundle bundle =  getIntent().getExtras();
        projectNo = bundle.getString("projectNo");
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
        tv_issueType = (TextView) findViewById(R.id.risk_list_type);
        tv_issueType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelector(tv_issueType, issueType, new OnSelectedResultCallback() {
                    @Override
                    public void onSelected(int i, TextView holder) {
                        if (!holder.getText().toString().isEmpty()){
                            issueTypeId = issueTypeList.get(i).getId();
                        }
                        pb.setVisibility(View.VISIBLE);
                        index = 1;
                        loadData(index);
                    }
                });
            }
        });
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.setClass(getApplicationContext(),AddRiskActivity.class);
//                intent.putExtra("type",3);
//                intent.putExtra("issuetypename",list.get(i).getIssueTypeName());
//                intent.putExtra("creater",list.get(i).getCreater());
//                intent.putExtra("creatdate",list.get(i).getCreatDate());
//                intent.putExtra("issuedesc",list.get(i).getIssueDesc());
//                intent.putExtra("issueId",list.get(i).getIssueId());
//                intent.putExtra("modifierDate",list.get(i).getModifierDate());
//                intent.putExtra("solution",list.get(i).getSolution());
//                intent.putExtra("state",list.get(i).getState());
//                startActivity(intent);
//            }
//        });
        Button add_risk = (Button) findViewById(R.id.risk_list_addrisk);
        add_risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAddRisk();
            }
        });
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);

    }

    /**
     * 添加风险
     */
    private void toAddRisk() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),AddRiskActivity.class);
        intent.putExtra("type",0);
        intent.putExtra("projectNo",projectNo);
        startActivity(intent);
    }

    private void loadData(final int indx) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("projectNo",projectNo);
        getmap.put("pageNo",indx);
        getmap.put("pageSize",10);
        if (!tv_issueType.getText().toString().isEmpty()){
            getmap.put("issueType",issueTypeId);
        }
        HttpManager.getInstance().post(CommonValues.GET_CUS_ISSUE, getmap, RiskListEntity.class, new HttpManager.ResultCallback<RiskListEntity>() {
            @Override
            public void onSuccess(String json, final RiskListEntity entity) throws InterruptedException {
                if (entity.getCode().equals("100")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            if (indx == 1){
                                list.clear();
                            }
                            if (entity.getData().size() == 0){
                                hasMore = false;
                            }
                            list.addAll(entity.getData());
                            listAdapter.notifyDataSetChanged();
                            lv.completeRefresh();
                        }
                    });
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });

        HttpManager.getInstance().post(CommonValues.QUERY_ISSUE_TYPE, getmap, IssueTypeEntity.class, new HttpManager.ResultCallback<IssueTypeEntity>() {

            @Override
            public void onSuccess(String json, IssueTypeEntity entity) throws InterruptedException {
                if (entity.getCode().equals("100")){
                    issueTypeList = entity.getData();
                    issueType = new String[issueTypeList.size()];
                    for (int i = 0; i < issueTypeList.size(); i++) {
                        issueType[i] = issueTypeList.get(i).getName();
                    }
                }

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (lv.isPullRefreshed()){
            return;
        }else {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),AddRiskActivity.class);
            intent.putExtra("type",3);
            intent.putExtra("issuetypename",list.get(i-1).getIssueTypeName());
            intent.putExtra("creater",list.get(i-1).getCreater());
            intent.putExtra("creatdate",list.get(i-1).getCreatDate());
            intent.putExtra("issuedesc",list.get(i-1).getIssueDesc());
            intent.putExtra("issueId",list.get(i-1).getIssueId());
            intent.putExtra("modifierName",list.get(i-1).getModifierName());
            intent.putExtra("modifierDate",list.get(i-1).getModifierDate());
            intent.putExtra("solution",list.get(i-1).getSolution());
            intent.putExtra("realname",list.get(i-1).getRealname());
            intent.putExtra("state",list.get(i-1).getState());
            startActivity(intent);
        }
    }
}
