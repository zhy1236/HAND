package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.CommentJournalActivity;
import com.example.hand.mockingbot.avtivity.DefectJournalActivity;
import com.example.hand.mockingbot.avtivity.NewJournalActivity;
import com.example.hand.mockingbot.avtivity.ReceivedJournalAtivity;
import com.example.hand.mockingbot.avtivity.SendJournalActivity;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.GetDailyStatisticalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.hand.mockingbot.R.id.journal_receiver;

/**
 * Created by zhy on 2017/5/3.
 */

public class JournalFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mJournal_receiver, mJournal_new, mJournal_send, mJournal_defect,mJournal_comment;
    private TextView received_num;
    private TextView send_num;
    private TextView defect_num;
    private TextView comment_num;
    private GetDailyStatisticalEntity getDailyStatisticalEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String url = CommonValues.JOURNAL_DAILY_STATISTICAL + "userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyTime=" + getData();
        HttpManager.getInstance().get(url, GetDailyStatisticalEntity.class, new HttpManager.ResultCallback<GetDailyStatisticalEntity>() {
            @Override
            public void onSuccess(String json, GetDailyStatisticalEntity getDailyStatistical) throws InterruptedException {
                getDailyStatisticalEntity = getDailyStatistical;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getDailyStatisticalEntity.getResult().getData().get(0).getIsNotNum() > 0){
                            received_num.setVisibility(View.VISIBLE);
                            received_num.setText(getDailyStatisticalEntity.getResult().getData().get(0).getIsNotNum() < 99?(getDailyStatisticalEntity.getResult().getData().get(0).getIsNotNum() + ""):"99+");
                        }
                        if (getDailyStatisticalEntity.getResult().getData().get(2).getIsNotNum() > 0){
                            send_num.setVisibility(View.VISIBLE);
                            send_num.setText(getDailyStatisticalEntity.getResult().getData().get(2).getIsNotNum() < 99?(getDailyStatisticalEntity.getResult().getData().get(2).getIsNotNum() + ""):"99+");
                        }
                        if (getDailyStatisticalEntity.getResult().getData().get(3).getIsNotNum() > 0){
                            defect_num.setVisibility(View.VISIBLE);
                            defect_num.setText(getDailyStatisticalEntity.getResult().getData().get(3).getIsNotNum() < 99?(getDailyStatisticalEntity.getResult().getData().get(3).getIsNotNum() + ""):"99+");
                        }
                        if (getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() > 0){
                            comment_num.setVisibility(View.VISIBLE);
                            comment_num.setText(getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() < 99?(getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() + ""):"99+");
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        received_num = (TextView) view.findViewById(R.id.fragment_journal_tv_received_num);
        send_num = (TextView) view.findViewById(R.id.fragment_journal_tv_send_num);
        defect_num = (TextView) view.findViewById(R.id.fragment_journal_tv_defect_num);
        comment_num = (TextView) view.findViewById(R.id.fragment_journal_tv_comment_num);
        mJournal_receiver = (RelativeLayout) view.findViewById(journal_receiver);
        mJournal_receiver.setOnClickListener(this);
        mJournal_new = (RelativeLayout) view.findViewById(R.id.journal_new);
        mJournal_new.setOnClickListener(this);
        mJournal_send = (RelativeLayout) view.findViewById(R.id.journal_send);
        mJournal_send.setOnClickListener(this);
        mJournal_defect = (RelativeLayout) view.findViewById(R.id.journal_defect);
        mJournal_defect.setOnClickListener(this);
        mJournal_comment = (RelativeLayout) view.findViewById(R.id.journal_comment);
        mJournal_comment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.journal_receiver:
                intent.setClass(getContext(), ReceivedJournalAtivity.class);
                intent.putExtra("content",getDailyStatisticalEntity.getResult().getData().get(0).getIsNotNum());
                break;
            case R.id.journal_new:
                intent.setClass(getContext(), NewJournalActivity.class);
                break;
            case R.id.journal_send:
                intent.setClass(getContext(), SendJournalActivity.class);
                intent.putExtra("content",getDailyStatisticalEntity.getResult().getData().get(2).getIsNotNum());
                break;
            case R.id.journal_defect:
                intent.setClass(getContext(), DefectJournalActivity.class);
                break;
            case R.id.journal_comment:
                intent.setClass(getContext(), CommentJournalActivity.class);
                break;
        }
        startActivity(intent);
    }

    public String getData(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}