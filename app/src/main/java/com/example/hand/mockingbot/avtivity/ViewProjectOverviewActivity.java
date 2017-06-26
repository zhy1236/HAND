package com.example.hand.mockingbot.avtivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.CusProjectStageEntity;
import com.example.hand.mockingbot.entity.CusProjectStateEmtity;
import com.example.hand.mockingbot.entity.UpdateCusProjectEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;

import java.util.List;
import java.util.Map;

import static com.example.hand.mockingbot.R.id.view_project_overview_ispoint_iv;

/**
 * Created by zhy on 2017/6/15.
 */

public class ViewProjectOverviewActivity extends BasicActivity implements View.OnClickListener {

    public AlertDialog dialog;
    private Bundle bundle;
    private Toolbar toolbar;
    private Button btn_change;
    private TextView tv_finish , project_num , project_name , isposint , risky ,project_manager ,
            product_manager ,start_time , over_time , stage , state , schedule;
    private ImageView ispoint_iv , risky_iv , stage_iv , state_iv;
    private LinearLayout isposint_ll , risky_ll , stage_ll , state_ll;
    private boolean change = false;
    private String[] args;
    private String[] args1;
    private TextView title;
    private RelativeLayout pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_overview);
        bundle = getIntent().getExtras();
        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pb.setVisibility(View.VISIBLE);
        changeOver();
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("productId",bundle.getString("id"));
        HttpManager.getInstance().post(CommonValues.GET_CUS_PROJECT_STATE, getmap, CusProjectStateEmtity.class, new HttpManager.ResultCallback<CusProjectStateEmtity>() {
            @Override
            public void onSuccess(String json, CusProjectStateEmtity cusProjectStateEmtity) throws InterruptedException {
                if (cusProjectStateEmtity.getCode().equals("100")){
                    List<CusProjectStateEmtity.DataBean> data = cusProjectStateEmtity.getData();
                    args = new String[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        args[i] = data.get(i).getState();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        HttpManager.getInstance().post(CommonValues.GET_CUS_PROJECT_STAGE, getmap, CusProjectStageEntity.class, new HttpManager.ResultCallback<CusProjectStageEntity>() {
            @Override
            public void onSuccess(String json, CusProjectStageEntity cusProjectStageEntity) throws InterruptedException {
                if (cusProjectStageEntity.getCode().equals("100")){
                    List<CusProjectStageEntity.DataBean> data = cusProjectStageEntity.getData();
                    args1 = new String[data.size()];
                    for (int i = 0; i < data.size(); i++) {
                        args1[i] = data.get(i).getStage();
                    }
                }
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
        title = (TextView) findViewById(R.id.main_tv_title);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
        btn_change = (Button) findViewById(R.id.journal_receiver_btn_search);
        btn_change.setVisibility(HandApp.getLoginEntity().getResult().getData().getAuthPermission().equals("1")?View.VISIBLE:View.GONE);
        btn_change.setOnClickListener(this);
        tv_finish = (TextView) findViewById(R.id.tooble_tv_finish);
        tv_finish.setOnClickListener(this);
        project_num = (TextView) findViewById(R.id.view_project_overview_project_num);
        project_num.setText(bundle.getString("id"));
        project_name = (TextView) findViewById(R.id.view_project_overview_project_name);
        project_name.setText(bundle.getString("name"));
        isposint = (TextView) findViewById(R.id.view_project_overview_ispoint);
        isposint.setText(bundle.getString("projectImprotance"));
        ispoint_iv = (ImageView) findViewById(view_project_overview_ispoint_iv);
        isposint_ll = (LinearLayout) findViewById(R.id.view_project_overview_ispoint_ll);
        isposint_ll.setOnClickListener(this);
        risky_ll = (LinearLayout) findViewById(R.id.view_project_overview_risky_ll);
        risky_ll.setOnClickListener(this);
        risky = (TextView) findViewById(R.id.view_project_overview_risky);
        risky.setText(bundle.getString("projectRisk"));
        risky_iv = (ImageView) findViewById(R.id.view_project_overview_risky_iv);
        project_manager = (TextView) findViewById(R.id.view_project_overview_project_manager);
        project_manager.setText(bundle.getString("projectManager"));
        product_manager = (TextView) findViewById(R.id.view_project_overview_product_manager);
        product_manager.setText(bundle.getString("cpjlName"));
        start_time = (TextView) findViewById(R.id.view_project_overview_start_time);
        start_time.setText(bundle.getString("beginTime"));
        over_time = (TextView) findViewById(R.id.view_project_overview_end_time);
        over_time.setText(bundle.getString("endtime"));
        stage_ll = (LinearLayout) findViewById(R.id.view_project_overview_stage_ll);
        stage_ll.setOnClickListener(this);
        stage = (TextView) findViewById(R.id.view_project_overview_stage);
        stage.setText(bundle.getString("projectStage"));
        stage_iv = (ImageView) findViewById(R.id.view_project_overview_stage_iv);
        state_ll = (LinearLayout) findViewById(R.id.view_project_overview_state_ll);
        state_ll.setOnClickListener(this);
        state = (TextView) findViewById(R.id.view_project_overview_state);
        state.setText(bundle.getString("projectState"));
        state_iv = (ImageView) findViewById(R.id.view_project_overview_state_iv);
        schedule = (TextView) findViewById(R.id.view_project_overview_schedule);
        schedule.setText(bundle.getString("overallProgress") + "%");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.journal_receiver_btn_search:
                change = true;
                btn_change.setVisibility(View.GONE);
                tv_finish.setVisibility(View.VISIBLE);
                risky_iv.setVisibility(View.GONE);
                ispoint_iv.setVisibility(View.VISIBLE);
                stage_iv.setVisibility(View.VISIBLE);
                state_iv.setVisibility(View.VISIBLE);
                title.setText("修改项目概览");
                break;
            case R.id.tooble_tv_finish:
                change = false;
                btn_change.setVisibility(View.VISIBLE);
                tv_finish.setVisibility(View.GONE);
                risky_iv.setVisibility(View.VISIBLE);
                ispoint_iv.setVisibility(View.GONE);
                stage_iv.setVisibility(View.GONE);
                state_iv.setVisibility(View.GONE);
                title.setText("查看项目概览");
                pb.setVisibility(View.VISIBLE);
                changeOver();
                break;
            case R.id.view_project_overview_ispoint_ll:
                if (change){
                    showDialog();
                }
                break;
            case R.id.view_project_overview_risky_ll:
                if (!change){
                    intent.setClass(getApplicationContext(),RiskListActivity.class);
                    intent.putExtra("projectNo",bundle.getString("projectNo"));
                    startActivity(intent);
                }
                break;
            case R.id.view_project_overview_stage_ll:
                if (change){
                    showSelector(stage,args1);
                }
                break;
            case R.id.view_project_overview_state_ll:
                if (change){
                    showSelector(state, args);
                }
                break;
        }
    }

    private void changeOver() {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("projectImprotance",isposint.getText().toString());
        getmap.put("projectStage",stage.getText().toString());
        getmap.put("projectState",state.getText().toString());
        getmap.put("productId",project_num.getText().toString());
        HttpManager.getInstance().post(CommonValues.UPDATE_CUS_PROJECT, getmap, UpdateCusProjectEntity.class, new HttpManager.ResultCallback<UpdateCusProjectEntity>() {
            @Override
            public void onSuccess(String json, final UpdateCusProjectEntity entity) throws InterruptedException {
                if (entity.getCode().equals("100")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            UpdateCusProjectEntity.DataBean dataBean = entity.getData().get(0);
                            project_num.setText(dataBean.getId() + "");
                            project_name.setText(dataBean.getName());
                            isposint.setText(dataBean.getProjectImprotance());
                            risky.setText(dataBean.getProjectRisk());
                            project_manager.setText(dataBean.getProjectManager());
                            product_manager.setText(dataBean.getCpjlName());
                            start_time.setText(dataBean.getBeginTime());
                            over_time.setText(dataBean.getEndTime());
                            stage.setText(dataBean.getProjectStage());
                            state.setText(dataBean.getProjectState());
                            schedule.setText(dataBean.getOverallProgress());
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).setItems(new CharSequence[]{"是","否"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isposint.setText("是");
                            }
                        });
                    } else if (which == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isposint.setText("否");
                            }
                        });
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
        }
        dialog.show();
    }
}
