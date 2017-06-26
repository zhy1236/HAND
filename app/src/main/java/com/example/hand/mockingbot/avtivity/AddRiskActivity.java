package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.Entity;
import com.example.hand.mockingbot.entity.IssueTypeEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.utils.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/26.
 */

public class AddRiskActivity extends BasicActivity{

    private Toolbar toolbar;
    private TextView check_type;
    private LinearLayout risk_type_ll;
    private String[] issueType;
    private TextView name;
    private EditText content;
    private Button btn_add;
    private String projectNo;
    private List<IssueTypeEntity.DataBean> issueTypeList;
    private int id_issueType;
    private RelativeLayout pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_info);
        projectNo = getIntent().getExtras().getString("projectNo");
        initView();
        loadData();
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
        check_type = (TextView) findViewById(R.id.risk_info_check_type);
        risk_type_ll = (LinearLayout) findViewById(R.id.risk_type_ll);
        risk_type_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelector(check_type, issueType, new OnSelectedResultCallback() {
                    @Override
                    public void onSelected(int i, TextView holder) {
                        id_issueType = issueTypeList.get(i).getId();
                    }
                });
            }
        });
        name = (TextView) findViewById(R.id.risk_info_name);
        name.setText(HandApp.getLoginEntity().getResult().getData().getRealname());
        content = (EditText) findViewById(R.id.risk_info_content);
        btn_add = (Button) findViewById(R.id.risk_info_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRisk();
            }
        });
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
    }

    private void addRisk() {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("projectNo",projectNo);
        if (check_type.getText().toString().isEmpty()){
            ToastUtil.showToast(getApplicationContext(),"请选择问题类型");
            return;
        }
        getmap.put("issueType",id_issueType);
        if (content.getText().toString().isEmpty()){
            ToastUtil.showToast(getApplicationContext(),"请填写问题描述");
            return;
        }
        getmap.put("issueDesc",content.getText().toString());
        getmap.put("creater",name.getText().toString());
        pb.setVisibility(View.VISIBLE);
        HttpManager.getInstance().post(CommonValues.SAVE_CUS_ISSUE, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
            @Override
            public void onSuccess(String json, final Entity entity) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        if (entity.getCode().equals("100")){
                            ToastUtil.showToast(getApplicationContext(),"添加问题成功!");
                            finish();
                        }else {
                            ToastUtil.showToast(getApplicationContext(),"添加问题失败!");
                        }
                    }
                });

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
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
}
