package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private int type;
    private Bundle bundle;
    private TextView title;
    private EditText solution;
    private TextView handle_type_tv;
    private int state = 0;
    private TextView handle_person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_info);
        bundle = getIntent().getExtras();
        projectNo = bundle.getString("projectNo");
        type = bundle.getInt("type");
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
        title = (TextView) findViewById(R.id.main_tv_title);
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
        LinearLayout creat_time_ll = (LinearLayout) findViewById(R.id.risk_info_creattime_ll);
        TextView tv_time =  (TextView) findViewById(R.id.risk_info_creattime);
        LinearLayout update_name = (LinearLayout) findViewById(R.id.risk_info_update_name_ll);
        TextView update_name_tv = (TextView) findViewById(R.id.risk_info_update_name);
        update_name_tv.setText(HandApp.getLoginEntity().getResult().getData().getRealname());
        LinearLayout update_time_ll = (LinearLayout) findViewById(R.id.risk_info_update_time_ll);
        TextView update_time_tv = (TextView) findViewById(R.id.risk_info_update_time);
        update_time_tv.setText(getData(true));
        btn_add = (Button) findViewById(R.id.risk_info_add);
        if (type != 0){
            title.setText("修改风险");
            check_type.setText(bundle.getString("issuetypename"));
            content.setText(bundle.getString("issuedesc"));
            name.setText(bundle.getString("creater"));
            creat_time_ll.setVisibility(View.VISIBLE);
            tv_time.setText(bundle.getString("creatdate"));
            update_name.setVisibility(View.VISIBLE);
            update_time_ll.setVisibility(View.VISIBLE);
            if (type != 1){
                title.setText("处理风险");
                ImageView type_iv = (ImageView) findViewById(R.id.risk_type_iv);
                type_iv.setVisibility(View.GONE);
                risk_type_ll.setEnabled(false);
                check_type.setTextColor(0xFF333333);
                content.setEnabled(false);
                content.setTextColor(0xFF333333);
                update_name_tv.setText(bundle.getString("modifierName"));
                update_time_tv.setText(bundle.getString("modifierDate"));
                LinearLayout solution_ll = (LinearLayout) findViewById(R.id.risk_info_solution_ll);
                solution_ll.setVisibility(View.VISIBLE);
                solution = (EditText) findViewById(R.id.risk_info_solution);
                LinearLayout handle_person_ll = (LinearLayout) findViewById(R.id.risk_info_handle_person_ll);
                handle_person_ll.setVisibility(View.VISIBLE);
                handle_person = (TextView) findViewById(R.id.risk_info_handle_person);
                handle_person.setText(HandApp.getLoginEntity().getResult().getData().getRealname());
                LinearLayout handle_type_ll = (LinearLayout) findViewById(R.id.risk_handle_type_ll);
                handle_type_ll.setVisibility(View.VISIBLE);
                handle_type_tv = (TextView) findViewById(R.id.risk_info_check_handle_type);
                handle_type_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showSelector(handle_type_tv, new String[]{"未完成","已完成"}, new OnSelectedResultCallback() {
                            @Override
                            public void onSelected(int i, TextView holder) {
                                state = i;
                            }
                        });
                    }
                });
                if (type == 3){
                    title.setText("查看风险");
                    handle_type_ll.setEnabled(false);
                    solution.setText(bundle.getString("solution"));
                    solution.setTextColor(0xff333333);
                    solution.setEnabled(false);
                    ImageView handle_type_iv = (ImageView) findViewById(R.id.risk_handle_type_iv);
                    handle_type_iv.setVisibility(View.GONE);
                    handle_person.setText(bundle.getString("realname"));
                    handle_type_tv.setText(bundle.getString("state").equals("0")?"未完成":"已完成");
                    handle_type_tv.setTextColor(0xff333333);
                    btn_add.setVisibility(View.GONE);
                }
            }
        }
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
        if (type != 0 && check_type.getText().toString().isEmpty()){
            ToastUtil.showToast(getApplicationContext(),"请选择问题类型");
            return;
        }
        getmap.put("issueType",id_issueType);
        if (type != 0 && content.getText().toString().isEmpty()){
            ToastUtil.showToast(getApplicationContext(),"请填写问题描述");
            return;
        }
        getmap.put("issueDesc",content.getText().toString());
        getmap.put("creater",name.getText().toString());
        pb.setVisibility(View.VISIBLE);
        if (type == 0){
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
                                ToastUtil.showToast(getApplicationContext(),"添加问题失败," + entity.getMsg());
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }else if (type == 1){
            getmap.put("issueId",bundle.getInt("issueId"));
            getmap.put("modifier",HandApp.getLoginEntity().getResult().getData().getId());
            HttpManager.getInstance().post(CommonValues.UPDATE_CUS_ISSUE, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
                @Override
                public void onSuccess(String json, final Entity entity) throws InterruptedException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            if (entity.getCode().equals("100")){
                                ToastUtil.showToast(getApplicationContext(),"修改问题成功!");
                                finish();
                            }else {
                                ToastUtil.showToast(getApplicationContext(),"修改问题失败," + entity.getMsg());
                            }
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }else if (type == 2){
            if (handle_type_tv.getText().toString().isEmpty()){
                ToastUtil.showToast(getApplicationContext(),"请选择处理状态");
                return;
            }
            if (solution.getText().toString().isEmpty()){
                ToastUtil.showToast(getApplicationContext(),"请填写解决方案");
                return;
            }
            getmap.put("issueId",bundle.getInt("issueId"));
            getmap.put("state",state);
            getmap.put("solution",solution.getText().toString());
            getmap.put("handler",HandApp.getLoginEntity().getResult().getData().getId());
            HttpManager.getInstance().post(CommonValues.DISPOSE_CUS_ISSUE, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
                @Override
                public void onSuccess(String json, final Entity entity) throws InterruptedException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            if (entity.getCode().equals("100")){
                                ToastUtil.showToast(getApplicationContext(),"处理问题成功!");
                                finish();
                            }else {
                                ToastUtil.showToast(getApplicationContext(),"处理问题失败," + entity.getMsg());
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }

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
