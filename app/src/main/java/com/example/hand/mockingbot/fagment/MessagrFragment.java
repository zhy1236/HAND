package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.CommentJournalActivity;
import com.example.hand.mockingbot.avtivity.ReceivedJournalAtivity;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.CommentAllEntity;
import com.example.hand.mockingbot.entity.GetDailyStatisticalEntity;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.entity.ReceivedJournalEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.HandApp;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by zhy on 2017/4/24.
 */

public class MessagrFragment extends android.support.v4.app.Fragment {


    private TextView received_num, comment_num, journal_title, message_title;
    private GetDailyStatisticalEntity getDailyStatisticalEntity;
    private RelativeLayout to_journal;
    private RelativeLayout to_comment;
    private TextView commet_time;
    private TextView journal_time;


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
        final LoginEntity.ResultBean.DataBean data = HandApp.getLoginEntity().getResult().getData();
        String url = CommonValues.JOURNAL_DAILY_STATISTICAL + "userId=" + data.getId() + "&dailyTime=" + getData();
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
                        }else {
                            received_num.setVisibility(View.INVISIBLE);
                        }
                        if (getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() > 0){
                            comment_num.setVisibility(View.VISIBLE);
                            comment_num.setText(getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() < 99?(getDailyStatisticalEntity.getResult().getData().get(4).getIsNotNum() + ""):"99+");
                        }else {
                            comment_num.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {

            }
        });


        String url2 = CommonValues.JOURNAL_LIST + "userId=" + data.getId() + "&dailyTime=" + getData() + "&state=0&pageNo=1&pageSize=10";
        HttpManager.getInstance().get(url2, ReceivedJournalEntity.class, new HttpManager.ResultCallback<ReceivedJournalEntity>() {
            @Override
            public void onSuccess(String json, final ReceivedJournalEntity receivedJournalEntity) throws InterruptedException {
                if (receivedJournalEntity.getResult().getData().size() > 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            journal_title.setText("[日报]" + receivedJournalEntity.getResult().getData().get(0).getRealname() + "的日报");
                            String joutnalTime = getData(receivedJournalEntity.getResult().getData().get(0).getSubmitDate());
                            journal_time.setText(joutnalTime.split("-")[1] + ":" + joutnalTime.split("-")[2]);
                        }
                    });
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });

        String url3 = CommonValues.GET_ALL_COMMENT + "userId=" + data.getId();
        HttpManager.getInstance().get(url3, CommentAllEntity.class, new HttpManager.ResultCallback<CommentAllEntity>() {
            @Override
            public void onSuccess(String json,final CommentAllEntity commentAllEntity) throws InterruptedException {
                commentAllEntity.getResult().getData();
                if (commentAllEntity.getResult().getData().size() > 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CommentAllEntity.ResultBean.DataBean dataBean = commentAllEntity.getResult().getData().get(0);
                            String[] split = dataBean.getSubmitDate().split(" ")[0].split("-");
                            String str = split[1].substring(1) + "月" + split[2] + "日";
                            message_title.setText("[日报]" + dataBean.getRealname() +  "对" + data.getRealname() + str + "提交的[日报]进行了评论");
                            String substring = dataBean.getCommentDate().split(" ")[1].substring(0, 5);
                            commet_time.setText(substring);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container,false);
        received_num = (TextView) view.findViewById(R.id.fragment_message_tv_received_num);
        comment_num = (TextView) view.findViewById(R.id.fragment_message_tv_comment_num);
        journal_title = (TextView) view.findViewById(R.id.message_tv_journal_title);
        message_title = (TextView) view.findViewById(R.id.message_tv_comment_title);
        to_journal = (RelativeLayout) view.findViewById(R.id.message_to_journal);
        to_journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ReceivedJournalAtivity.class);
                startActivity(intent);
            }
        });
        to_comment = (RelativeLayout) view.findViewById(R.id.message_to_comment);
        to_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), CommentJournalActivity.class);
                startActivity(intent);
            }
        });
        commet_time = (TextView) view.findViewById(R.id.message_tv_comment_time);
        journal_time = (TextView) view.findViewById(R.id.message_tv_jurnal_time);
        return view;
    }



    public String getData(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public String getData(long lon){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(lon);
        String str = formatter.format(curDate);
        return str;
    }
}
