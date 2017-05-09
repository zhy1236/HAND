package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.MyFragmentPagerAdapter;
import com.example.hand.mockingbot.fagment.JournalFragment;
import com.example.hand.mockingbot.fagment.MessagrFragment;
import com.example.hand.mockingbot.fagment.MyFragment;
import com.example.hand.mockingbot.fagment.ObjectFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.fl_content);
        fragments = getData();
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter mfpa = new MyFragmentPagerAdapter(fm, fragments);
        pager.setAdapter(mfpa);
        pager.setOnPageChangeListener(new TabOnPageChangeListener());

        radioGroup=(RadioGroup) findViewById(R.id.bottom_bar);
        object = (RadioButton) findViewById(R.id.rb_main_object);
        message=(RadioButton) findViewById(R.id.rb_main_message);
        journal=(RadioButton) findViewById(R.id.rb_main_journal);
        object.setChecked(true);
        my = (RadioButton) findViewById(R.id.rb_main_my);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_object:
                        pager.setCurrentItem(0);//选择某一页
                        break;
                    case R.id.rb_main_message:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.rb_main_journal:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.rb_main_my:
                        pager.setCurrentItem(3);
                        break;
                }
            }
        });


    }

    private List<Fragment> getData() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new ObjectFragment());
        fragments.add(new MessagrFragment());
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
                case 0:
                    object.setChecked(true);
                    break;
                case 1:
                    message.setChecked(true);
                    break;
                case 2:
                    journal.setChecked(true);
                    break;
                case 3:
                    my.setChecked(true);
                    break;

            }
        }
    }
}
