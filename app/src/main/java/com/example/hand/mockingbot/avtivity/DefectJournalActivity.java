package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;

/**
 * Created by zhy on 2017/5/5.
 */

public class DefectJournalActivity extends BasicActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defect_journal);
        initView();
        lodDate();

    }

    private void lodDate() {
        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData(true) + "&state=1&pageNo=" + 1 + "&pageSize=10";
        HttpManager.getInstance().get(url, ReceivedJournalEntity.class, new HttpManager.ResultCallback<ReceivedJournalEntity>() {
            @Override
            public void onSuccess(String json, ReceivedJournalEntity receivedJournal) throws InterruptedException {
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
