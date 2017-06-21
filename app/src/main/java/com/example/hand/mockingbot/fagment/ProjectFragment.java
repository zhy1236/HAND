package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.avtivity.ProjectOverviewActivity;
import com.example.hand.mockingbot.avtivity.ResourceOccupationActivity;
import com.example.hand.mockingbot.entity.AttentionProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/3.
 */

public class ProjectFragment extends Fragment {

    private int[] im = {R.mipmap.ic_project_overview,R.mipmap.ic_resource_occupancy,R.mipmap.ic_task_statistics,R.mipmap.ic_project_progress,
                                R.mipmap.ic_project_risk,R.mipmap.ic_project_announcement,R.mipmap.ic_memorandum,R.mipmap.ic_all};

    private String[] tv = {"项目概览","资源占用","任务统计","项目进展","项目风险","项目公告","备忘录","全部"};
    private Class[]  classes = {ProjectOverviewActivity.class,ResourceOccupationActivity.class};
    private List<AttentionProject> list = new ArrayList<>();
    private ListView lv;
    private ListAdapter<AttentionProject> listAdapter = new ListAdapter<AttentionProject>(list, R.layout.attention_project_item) {
        @Override
        public void bindView(ViewHolder holder, AttentionProject obj,int position) {
            holder.setText(R.id.attention_project_item_tv,obj.getProjectName());
            holder.setImageResource(R.id.attention_project_item_iv,obj.getProjectImage());
        }
    };
    private ScrollView sl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        //网络获取数据

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container,false);
        GridLayout gl = (GridLayout) view.findViewById(R.id.project_gl);
        for (int i = 0; i < 8; i++) {
            WindowManager wm = getActivity().getWindowManager();
            int width = wm.getDefaultDisplay().getWidth();
            RelativeLayout.LayoutParams lp =new RelativeLayout.LayoutParams(width/4, ViewPager.LayoutParams.WRAP_CONTENT);
            final RelativeLayout item = (RelativeLayout) View.inflate(getContext(), R.layout.project_item, null);
            item.setLayoutParams(lp);
            ImageView item_iv = (ImageView) item.findViewById(R.id.project_item_iv);
            item_iv.setBackgroundResource(im[i]);
            TextView item_tv = (TextView) item.findViewById(R.id.project_item_tv);
            item_tv.setText(tv[i]);
            final int finalI = i;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == classes.length - 1){
                        Intent intent = new Intent();
                        intent.setClass(getContext(), classes[finalI]);
                        startActivity(intent);
                    }
                }
            });
            gl.addView(item);
        }
        initlist();
        lv = (ListView) view.findViewById(R.id.project_lv);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
        lv.setFocusable(false);
        sl = (ScrollView) view.findViewById(R.id.project_sl);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        sl.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void onResume() {
        super.onResume();
        sl.fullScroll(ScrollView.FOCUS_UP);

    }

    private void initlist() {
        list.clear();
        AttentionProject attentionProject1 = new AttentionProject();
        attentionProject1.setProjectName("伊利智能BI平台");
        attentionProject1.setProjectImage(R.mipmap.ic_project_yili);
        list.add(attentionProject1);
        AttentionProject attentionProject2 = new AttentionProject();
        attentionProject2.setProjectName("项目综合管理平台");
        attentionProject2.setProjectImage(R.mipmap.ic_hand);
        list.add(attentionProject2);
        AttentionProject attentionProject3 = new AttentionProject();
        attentionProject3.setProjectName("吉利统一流程平台");
        attentionProject3.setProjectImage(R.mipmap.ic_geely);
        list.add(attentionProject3);
        AttentionProject attentionProject4 = new AttentionProject();
        attentionProject4.setProjectName("DMP管理系统");
        attentionProject4.setProjectImage(R.mipmap.ic_hand);
        list.add(attentionProject4);

    }
}