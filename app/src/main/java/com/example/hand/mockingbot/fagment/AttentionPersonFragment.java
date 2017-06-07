package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.avtivity.PersonListActivity;
import com.example.hand.mockingbot.avtivity.SendJournalActivity;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AttentionPerson;
import com.example.hand.mockingbot.entity.Entity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.ToastUtil;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/1.
 */

public class AttentionPersonFragment extends Fragment implements SimpleListView.OnRefreshListener{

    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<AttentionPerson.DataBean> list = new ArrayList<>();
    private ListAdapter<AttentionPerson.DataBean> listAdapter = new ListAdapter<AttentionPerson.DataBean>(list, R.layout.item_attention_person) {
        @Override
        public void bindView(ViewHolder holder, final AttentionPerson.DataBean obj, final int position) {
            holder.setText(R.id.item_attention_person_name,obj.getRealname());
            holder.setText(R.id.item_attention_person_zw,obj.getPosition());
            holder.setOnClickListener(R.id.item_attention_person_journal, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toPersonJournals(obj);
                }
            });
            holder.setOnClickListener(R.id.item_attention_person_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unfocus(position,obj);
                }
            });
        }
    };

    private void toPersonJournals(AttentionPerson.DataBean obj) {
        Intent intent = new Intent();
        intent.setClass(getContext(), SendJournalActivity.class);
        intent.putExtra("userId", obj.getMemberId() + "");
        startActivity(intent);
    }

    private RelativeLayout pb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(index);
    }

    private void loadData(final int idx) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("state",1);
        getmap.put("pageNo",idx);
        getmap.put("pageSize",10);
        HttpManager.getInstance().post(CommonValues.QUERY_USERS_ATTENTION, getmap, AttentionPerson.class, new HttpManager.ResultCallback<AttentionPerson>() {
            @Override
            public void onSuccess(String json, AttentionPerson attentionPerson) throws InterruptedException {
                final List<AttentionPerson.DataBean> data = attentionPerson.getData();
                if (index == 1){
                    list.clear();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        list.addAll(data);
                        listAdapter.notifyDataSetChanged();
                        hasMore = data.size()>0;
                        lv.completeRefresh();
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        lv.completeRefresh();
                    }
                });
            }
        });
    }

    private void unfocus(final int position, final AttentionPerson.DataBean dataBean) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("memberId",dataBean.getMemberId());
        getmap.put("type",1);
        getmap.put("state",0);
        HttpManager.getInstance().post(CommonValues.USER_FOCUS, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
            @Override
            public void onSuccess(String json, Entity entity) throws InterruptedException {
                if (entity.getCode() == 100){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.remove(position);
                            listAdapter.notifyDataSetChanged();
                            ToastUtil.showToast(getContext(),"取消对" + dataBean.getRealname() + "的关注成功");
                        }
                    });
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.item_list, null);
        lv = (SimpleListView) view.findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setDividerHeight(1);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) view.findViewById(R.id.journal_receiver_pb);
        pb.setVisibility(View.GONE);
        LinearLayout title = (LinearLayout) view.findViewById(R.id.item_list_title);
        title.setVisibility(View.VISIBLE);
        RelativeLayout bottom = (RelativeLayout) view.findViewById(R.id.item_list_bottom);
        bottom.setVisibility(View.VISIBLE);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotopersonlist();
            }
        });
        return view;
    }

    private void gotopersonlist() {
        Intent intent = new Intent();
        intent.setClass(getContext(),PersonListActivity.class);
        startActivity(intent);
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
}
