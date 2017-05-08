package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.MyFragmentPagerAdapter;
import com.example.hand.mockingbot.fagment.JournalFragment;
import com.example.hand.mockingbot.fagment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    private RadioButton object;
    private RadioButton message;
    private RadioButton journal;
    private RadioButton my;
    private ViewPager pager;
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        //设置左侧图标和点击事件
//        toolbar.setNavigationIcon(R.mipmap.ic_back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.fl_content);
        title = (TextView) findViewById(R.id.main_tv_title);
        fragments = getData();
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter mfpa = new MyFragmentPagerAdapter(fm, fragments);
        pager.setAdapter(mfpa);
        pager.setOnPageChangeListener(new TabOnPageChangeListener());

        radioGroup=(RadioGroup) findViewById(R.id.bottom_bar);
        object = (RadioButton) findViewById(R.id.rb_main_object);
        message=(RadioButton) findViewById(R.id.rb_main_message);
        journal=(RadioButton) findViewById(R.id.rb_main_journal);
        journal.setChecked(true);
        title.setText("日志");
        my = (RadioButton) findViewById(R.id.rb_main_my);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
//                    case R.id.rb_main_object:
//                        pager.setCurrentItem(0);//选择某一页
//                        break;
//                    case R.id.rb_main_message:
//                        pager.setCurrentItem(1);
//                        title.setText("消息");
//                        break;
                    case R.id.rb_main_journal:
                        pager.setCurrentItem(0);
                        title.setText("日志");
                        break;
                    case R.id.rb_main_my:
                        pager.setCurrentItem(1);
                        title.setText("我的");
                        break;
                }
            }
        });


    }

    private List<Fragment> getData() {
        fragments = new ArrayList<Fragment>();
//        fragments.add(new ObjectFragment());
//        fragments.add(new MessagrFragment());
        fragments.add(new JournalFragment());
        fragments.add(new MyFragment());
        return fragments;
    }


    /**
     * 页卡改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            switch (position) {
//                case 0:
//                    object.setChecked(true);
//                    title.setVisibility(View.GONE);
//                    break;
//                case 1:
//                    message.setChecked(true);
//                    title.setText("消息");
//                    break;
                case 0:
                    journal.setChecked(true);
                    title.setText("日志");
                    break;
                case 1:
                    my.setChecked(true);
                    title.setText("我的");
                    break;

            }
        }
    }
}
