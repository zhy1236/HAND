package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.CommentAllEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.view.SimpleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class CommentJournalActivity extends BasicActivity {

    private Toolbar toolbar;
    private boolean hasMore = true;
    private SimpleListView lv;
    private int index = 1;
    private List<CommentAllEntity.ResultBean.DataBean> list = new ArrayList<>();
    private ListAdapter<CommentAllEntity.ResultBean.DataBean> listAdapter = new ListAdapter<CommentAllEntity.ResultBean.DataBean>(list, R.layout.journal_item) {
        @Override
        public void bindView(ViewHolder holder, CommentAllEntity.ResultBean.DataBean obj) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_journal);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(index);
    }

    private void loadData(int ind) {
        String url = CommonValues.GET_ALL_COMMENT + "userId=" + HandApp.getLoginEntity().getResult().getData().getId();
        HttpManager.getInstance().get(url, CommentAllEntity.class, new HttpManager.ResultCallback<CommentAllEntity>() {
            @Override
            public void onSuccess(String json, CommentAllEntity commentAllEntity) throws InterruptedException {
                commentAllEntity.getResult().getData();
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
    }

}
