package com.example.hand.mockingbot.avtivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.entity.JournalBean;
import com.example.hand.mockingbot.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class ReceivedJournalAtivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<JournalBean> list = new ArrayList<>();
    private Toolbar toolbar;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorAccent);
        setContentView(R.layout.activity_received_journal);
        initToolbar();
        initView();
        initData();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
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
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.journal_receiver_lv);
        for (int i = 0; i < 10; i++) {
            JournalBean journalBean = new JournalBean();
            list.add(journalBean);
        }
        ListAdapter<JournalBean> listAdapter = new ListAdapter<JournalBean>(list,R.layout.journal_item) {
            @Override
            public void bindView(ViewHolder holder, JournalBean obj) {
                String str = "今日完成工作：<font color='#333333'>" + "今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作，今日完成工作" + "</font>";
                holder.setText(R.id.journal_item_tv_finish, Html.fromHtml(str));
            }
        };
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);

    }

    private void initData() {
        //网络请求拿到数据
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),LookUpJournalActivity.class);
        startActivity(intent);
    }
}
