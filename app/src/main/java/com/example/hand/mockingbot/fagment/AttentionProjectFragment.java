package com.example.hand.mockingbot.fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.AttentionProject;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/6/1.
 */

public class AttentionProjectFragment extends Fragment implements SimpleListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<AttentionProject> list = new ArrayList<>();
    private ListAdapter<AttentionProject> listAdapter = new ListAdapter<AttentionProject>(list, R.layout.item_attention_project) {
        @Override
        public void bindView(ViewHolder holder, AttentionProject obj, final int position) {
            holder.setImageResource(R.id.item_attention_project_iv,obj.getProjectImage());
            holder.setText(R.id.item_attention_project_tv,obj.getProjectName());
            holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(position);
                            listAdapter.notifyDataSetChanged();}
                    });
                }
            });
        }
    };
    private RelativeLayout pb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initList() {
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
        AttentionProject attentionProject5 = new AttentionProject();
        attentionProject5.setProjectName("伊利只能BI平台");
        attentionProject5.setProjectImage(R.mipmap.ic_project_yili);
        list.add(attentionProject5);
        AttentionProject attentionProject6 = new AttentionProject();
        attentionProject6.setProjectName("项目综合管理平台");
        attentionProject6.setProjectImage(R.mipmap.ic_hand);
        list.add(attentionProject6);
        AttentionProject attentionProject7 = new AttentionProject();
        attentionProject7.setProjectName("吉利统一流程平台");
        attentionProject7.setProjectImage(R.mipmap.ic_geely);
        list.add(attentionProject7);
        AttentionProject attentionProject8 = new AttentionProject();
        attentionProject8.setProjectName("DMP管理系统");
        attentionProject8.setProjectImage(R.mipmap.ic_hand);
        list.add(attentionProject8);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(index);
        initList();
    }

    private void loadData(final int index) {
//        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
//        String url = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData(true) + "&state=0&pageNo=" + index + "&pageSize=10";
//        HttpManager.getInstance().get(url, ReceivedJournalEntity.class, new HttpManager.ResultCallback<ReceivedJournalEntity>() {
//            @Override
//            public void onSuccess(String json, final ReceivedJournalEntity receivedJournalEntity) throws InterruptedException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
//                        if (index == 1){
//                            list.clear();
//                        }
//                        list.addAll(receivedJournalEntity.getResult().getData());
//                        hasMore = receivedJournalEntity.getResult().getData().size() > 0;
//                        if (list.size() < receivedJournalEntity.getResult().getPage().getTotal_elements()){
//                            hasMore = true;
//                        }else {
//                            hasMore = false;
//                        }
                        listAdapter.notifyDataSetChanged();
                        lv.completeRefresh();
                    }
                });
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pb.setVisibility(View.GONE);
//                        lv.completeRefresh();
//                    }
//                });
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.item_list, null);
        lv = (SimpleListView) view.findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);
        pb = (RelativeLayout) view.findViewById(R.id.journal_receiver_pb);
        pb.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onPullRefresh() {
        hasMore = true;
        index = 1;
        loadData(index);
//        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingMore() {
        lv.completeRefresh();
    }

    @Override
    public void onScrollOutside() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
