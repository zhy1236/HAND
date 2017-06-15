package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ResourcePersonEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.DataUtil;
import com.example.hand.mockingbot.utils.DateUtils;

import java.util.List;
import java.util.Map;

import static com.example.hand.mockingbot.R.id.item_resource_person_gl_tv;

/**
 * Created by zhy on 2017/6/8.
 */

public class ResourcePersonActivity extends BasicActivity {

    private Toolbar toolbar;
    private GridLayout gl;
    private TextView name;
    private TextView zw;
    private int monthDays;
    private int firstDayWeek;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_person);
        Bundle extras = getIntent().getExtras();
        time = extras.getString("time");
        initView();
        loadData();
    }

    private void initView() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
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
        gl = (GridLayout) findViewById(R.id.resource_person_gl);
        TextView year = (TextView) findViewById(R.id.reader_tv_year);
        year.setText(time.split("-")[0]);
        monthDays = DateUtils.getMonthDays(DataUtil.parseString2UnsignedInt(time.split("-")[0]), DataUtil.parseString2UnsignedInt(time.split("-")[1])-1);
        firstDayWeek = DateUtils.getFirstDayWeek(DataUtil.parseString2UnsignedInt(time.split("-")[0]), DataUtil.parseString2UnsignedInt(time.split("-")[1])-1) - 1;
        LinearLayout month_ll = (LinearLayout) findViewById(R.id.resource_month_ll);
        for (int i = 0; i < 7; i++) {
            if (i == firstDayWeek){
                TextView childAt = (TextView) month_ll.getChildAt(i);
                childAt.setText(DataUtil.parseString2UnsignedInt(time.split("-")[1]) + "月");
            }
        }

        for (int i = 0; i < monthDays + firstDayWeek; i++) {
            LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(width/7, 140);
            View inflate = View.inflate(getApplicationContext(), R.layout.item_resource_person_gl, null);
            Button tv = (Button) inflate.findViewById(item_resource_person_gl_tv);
            RelativeLayout rl = (RelativeLayout) inflate.findViewById(R.id.item_resource_person_gl_rl);
            rl.setLayoutParams(lp);
            if (i < firstDayWeek){
                tv.setText("");
            }else {
                tv.setText((i+1- firstDayWeek) + "");
            }
            if (i == 0 || i % 7 == 6 || i % 7 == 0){
                tv.setTextColor(ResourcePersonActivity.this.getResources().getColor(R.color.selector_textcolor3));
            }

            gl.addView(inflate);
        }
        name = (TextView) findViewById(R.id.resource_person_name);
        zw = (TextView) findViewById(R.id.resource_person_zw);
    }

    private void loadData() {
        final Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("product","");
        getmap.put("dateTime",time);
        getmap.put("pageNo",1);
        getmap.put("pageSize",10);
        getmap.put("resourceId",getIntent().getExtras().getInt("resourceId"));
        HttpManager.getInstance().post(CommonValues.RESOURCE_LIST, getmap, ResourcePersonEntity.class, new HttpManager.ResultCallback<ResourcePersonEntity>() {
            @Override
            public void onSuccess(String json, final ResourcePersonEntity entity) throws InterruptedException {
                if (entity.getCode().equals("100")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(entity.getData().get(0).getRealname());
                            zw.setText(entity.getData().get(0).getPosition());
                            List<Integer> workday = entity.getData().get(0).getWorkday();
                            for (final Integer integer : workday) {
                                View childAt = gl.getChildAt(integer + firstDayWeek - 1);
                                Button btn = (Button) childAt.findViewById(R.id.item_resource_person_gl_tv);
                                btn.setEnabled(true);
                                if ((integer + firstDayWeek) % 7 == 1 || (integer + firstDayWeek) % 7 == 0){
                                    btn.setTextColor(Color.WHITE);
                                }
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        toDailywork(integer);
                                    }
                                });
                            }
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

    private void toDailywork(Integer integer) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),ResourceDailyActivity.class);
        intent.putExtra("time",time);
        intent.putExtra("resourceId",getIntent().getExtras().getInt("resourceId"));
        intent.putExtra("day",integer);
        startActivity(intent);
    }


}
