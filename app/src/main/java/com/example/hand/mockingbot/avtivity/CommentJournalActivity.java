package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.CommentAllEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.utils.ToastUtil;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class CommentJournalActivity extends BasicActivity implements SimpleListView.OnRefreshListener {

    private Toolbar toolbar;
    private SimpleListView lv;
    private RelativeLayout pb;
    private List<CommentAllEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<CommentAllEntity.ResultBean.DataBean> listAdapter = new ListAdapter<CommentAllEntity.ResultBean.DataBean>(list, R.layout.item_comment) {
        @Override
        public void bindView(ViewHolder holder, CommentAllEntity.ResultBean.DataBean obj, final int position) {
            String submitDate = obj.getSubmitDate();
            String[] split = submitDate.split("-");
            if (split[1].startsWith("0")){
                String str = split[1].substring(1) + "月" + split[2] + "日";
                holder.setText(R.id.item_comment_time,str + obj.getCommentDate().split(" ")[1].substring(0,5));
                String title = obj.getRealname() + "对" + HandApp.getLoginEntity().getResult().getData().getRealname() + str + "提交的[日报]进行了评论";
                holder.setText(R.id.item_comment_tv_title,title);
            }else {
                String str = split[1] + "月" + split[2] + "日";
                holder.setText(R.id.item_comment_time,str + obj.getCommentDate().split(" ")[1].substring(0,5));
                String title = obj.getRealname() + "对" + HandApp.getLoginEntity().getResult().getData().getRealname() + str + "提交的[日报]进行了评论";
                holder.setText(R.id.item_comment_tv_title,title);
            }
            if (position != 0 &&(obj.getCommentDate().split(" ")[1].startsWith(list.get(position-1).getCommentDate().split(" ")[1].substring(0,5)))){
                holder.setVisibility(R.id.item_comment_time,View.GONE);
            }else {
                holder.setVisibility(R.id.item_comment_time,View.VISIBLE);
            }
            holder.setText(R.id.item_comment_ed,"评论内容：" + obj.getContent());
            holder.setOnClickListener(R.id.item_comment_ll, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
                            intent.putExtra("dailyId",list.get(position).getDailyId());
                            intent.putExtra("time",list.get(position).getCommentDate());
                            intent.putExtra("name",list.get(position).getRealname());
                            intent.putExtra("focus","0");
                            intent.putExtra("my",false);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_journal);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String url = CommonValues.GET_ALL_COMMENT + "userId=" + HandApp.getLoginEntity().getResult().getData().getId();
        HttpManager.getInstance().get(url, CommentAllEntity.class, new HttpManager.ResultCallback<CommentAllEntity>() {
            @Override
            public void onSuccess(String json,final CommentAllEntity commentAllEntity) throws InterruptedException {
                commentAllEntity.getResult().getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        pb.setVisibility(View.GONE);
                        list.addAll(commentAllEntity.getResult().getData());
                        listAdapter.notifyDataSetChanged();
                        lv.completeRefresh();
                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getApplicationContext(),"未获取到评论数据");
                        finish();
                    }
                });
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
        TextView textView = (TextView) findViewById(R.id.main_tv_title);
        textView.setText("评论");
        lv = (SimpleListView) findViewById(R.id.journal_receiver_lv);
        lv.setOnRefreshListener(this);
        lv.setAdapter(listAdapter);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
    }

    @Override
    public void onPullRefresh() {
        loadData();
    }

    @Override
    public void onLoadingMore() {
        lv.completeRefresh();
    }

    @Override
    public void onScrollOutside() {

    }

}
