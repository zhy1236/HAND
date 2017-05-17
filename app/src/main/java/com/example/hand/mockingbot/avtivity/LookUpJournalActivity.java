package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.entity.JournalBean;
import com.example.hand.mockingbot.utils.GsonUtil;
import com.example.hand.mockingbot.utils.HandApp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 2017/5/8.
 */

public class LookUpJournalActivity extends BasicActivity {

    private Toolbar toolbar;
    private Button right;
    private JournalBean journalBean;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_lookup_journal);
        initToolbar();
        initData();

    }

    private void initData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        intent = getIntent();
        int dailyId = intent.getExtras().getInt("dailyId");
        final Request request = new Request.Builder()
                .url("http://10.211.55.174:9090/project-mg-app/app/daily/comment?userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyId=" + dailyId)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                Log.e("asdasd",message);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String string = null;
                        try {
                            string = response.body().string();
                            journalBean = GsonUtil.parseJsonToBean(string, JournalBean.class);
                            initView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }


    private void initView() {
        TextView name = (TextView) findViewById(R.id.journal_item_tv_name);
        name.setText(intent.getExtras().getString("name"));
        TextView submittime = (TextView) findViewById(R.id.journal_item_tv_time);
        submittime.setText(intent.getExtras().getString("time"));
        TextView finishwork =  (TextView) findViewById(R.id.lookup_journal_finishwork);
        finishwork.setText(journalBean.getResult().getData().getFinishWork());
        TextView unfinishwork =  (TextView) findViewById(R.id.lookup_journal_unfinishwork);
        unfinishwork.setText(journalBean.getResult().getData().getUnfinishWork());
        TextView coordinationWork =  (TextView) findViewById(R.id.lookup_journal_coordinationWork);
        coordinationWork.setText(journalBean.getResult().getData().getCoordinationWork());
        TextView remark = (TextView) findViewById(R.id.lookup_journal_remark);
        remark.setText(journalBean.getResult().getData().getRemark());
        String enclosureUrl = journalBean.getResult().getData().getEnclosureUrl();
//        String[] split = enclosureUrl.split(",");
//        GridLayout gl = (GridLayout) findViewById(R.id.lookup_journal_gl);
//        for (int i = 0; i < split.length; i++) {
//            final View inflate = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
//            TextView fieldname = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
//            fieldname.setText(split[i].split("_")[2]);
//            ImageView viewById = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
//            viewById.setBackgroundResource(R.mipmap.ic_word);
////        }else if (extension.endsWith(".doc")){
////            viewById.setImageResource(R.mipmap.ic_word);
////        }else if (extension.endsWith(".ppt")) {
////            viewById.setImageResource(R.mipmap.ic_ppt);
////        }
//            TextView sise = (TextView) inflate.findViewById(R.id.attauch_item_field_sise);
//            Integer integer = new Integer(split[i].split("_")[1]);
//            sise.setText(integer.intValue()/1024 + "k");
//            final Button btn = (Button) inflate.findViewById(R.id.attauvh_item_btn);
//            btn.setVisibility(View.VISIBLE);
//            final ProgressBar pb = (ProgressBar) inflate.findViewById(R.id.attauch_item_pb);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (btn.getText().equals("下载")){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),"正在下载",Toast.LENGTH_SHORT).show();
//                                btn.setVisibility(View.GONE);
//                                btn.setText("打开");
//                                pb.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }else if (btn.getText().equals("打开")){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),"打开附件",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
//            });
//            pb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn.setVisibility(View.VISIBLE);
//                            pb.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
//            gl.addView(inflate,0);
//        }
        journalBean.getResult().getComment();
        journalBean.getResult().getCount();


    }

    private void initToolbar() {
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
        right = (Button) findViewById(R.id.main_bt_right);
    }
}
