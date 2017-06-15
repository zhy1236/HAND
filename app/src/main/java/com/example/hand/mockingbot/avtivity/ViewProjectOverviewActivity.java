package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.utils.ToastUtil;

import static com.example.hand.mockingbot.R.id.view_project_overview_ispoint_iv;

/**
 * Created by zhy on 2017/6/15.
 */

public class ViewProjectOverviewActivity extends BasicActivity implements View.OnClickListener {

    private Bundle bundle;
    private Toolbar toolbar;
    private Button btn_change;
    private TextView tv_finish , project_num , project_name , isposint , risky ,project_manager ,
            product_manager ,start_time , over_time , stage , state , schedule;
    private ImageView ispoint_iv , risky_iv , stage_iv , state_iv;
    private LinearLayout isposint_ll , risky_ll , stage_ll , state_ll;
    private boolean change = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_overview);
        bundle = getIntent().getExtras();
        initView();
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
        btn_change = (Button) findViewById(R.id.journal_receiver_btn_search);
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
        project_manager.setText(bundle.getString("cpjlName"));
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
        stage.setText(bundle.getString("projectState"));
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
                break;
            case R.id.tooble_tv_finish:
                change = false;
                btn_change.setVisibility(View.VISIBLE);
                tv_finish.setVisibility(View.GONE);
                risky_iv.setVisibility(View.VISIBLE);
                ispoint_iv.setVisibility(View.GONE);
                stage_iv.setVisibility(View.GONE);
                state_iv.setVisibility(View.GONE);
                break;
            case R.id.view_project_overview_ispoint_ll:
                if (change){
                    ToastUtil.showToast(getApplicationContext(),"修改是否重点");
                }
                break;
            case R.id.view_project_overview_risky_ll:
                if (!change){
                    ToastUtil.showToast(getApplicationContext(),"修改风险数量");
                    intent.setClass(getApplicationContext(),RiskListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.view_project_overview_stage_ll:
                if (change){
                    ToastUtil.showToast(getApplicationContext(),"修改阶段");
                }
                break;
            case R.id.view_project_overview_state_ll:
                if (change){
                    ToastUtil.showToast(getApplicationContext(),"修改状态");
                }
                break;
        }
    }
}
