package com.example.hand.mockingbot.fagment;

import android.view.View;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;

/**
 * Created by zhy on 2017/5/23.
 */

public class ReceivedJournalFragment extends BasicFragment {

    private ReceivedJournalEntity receivedJournalEntity;

    @Override
    public Object threadData() {
        LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData(true) + "&state=0&pageNo=" + 1 + "&pageSize=10";
        HttpManager.getInstance().get(url, ReceivedJournalEntity.class, new HttpManager.ResultCallback<ReceivedJournalEntity>() {
            @Override
            public void onSuccess(String json, ReceivedJournalEntity receivedJournal) throws InterruptedException {
                receivedJournalEntity = receivedJournal;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onFailure(String msg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
        return receivedJournalEntity;
    }

    @Override
    public View createView() {
        View inflate = View.inflate(getContext(), R.layout.activity_received_journal, null);
        return inflate;
    }


}
